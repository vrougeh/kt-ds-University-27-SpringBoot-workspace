/*
회원페이지와 관련된 스크립트 작성
*/
$().ready(function () {
  $("#email").on("blur", function () {
    setTimeout(function () {
      $("#email").trigger("keyup");
    }, 150);
  });

  var keyUpStartTime = new Date().getTime();
  $("#email").on("keyup", function () {
    var emailValue = $(this).val();
    var nowTime = new Date().getTime();

    if (nowTime - keyUpStartTime < 100) {
      return;
    }
    keyUpStartTime = nowTime;
    //이메일을 입력했을 때
    var emailParttern = /\b[\w\.-]+@[\w\.-]+\.\w{2,4}\b/;
    if (emailParttern.test(emailValue)) {
      //비동기로 중복여부를 검사해 온다.
      fetch("/regist/check/duplicate/" + emailValue)
        .then(function (fetchResult) {
          return fetchResult.json();
        })
        .then(function (json) {
          // console.log(json);
          var duplicateResult = $("#email")
            .closest(".input-div")
            .children(".validation-error");
          if (duplicateResult.length === 0) {
            var duplicateResult = $("#email")
              .closest(".input-div")
              .children(".validation-ok");
          }

          if (duplicateResult.length === 0) {
            var duplicateResult = $("<div>");
            $("#email").after(duplicateResult);
          }

          //비동기 결과를 이용하여 메세지 노출
          if (!json.duplicate) {
            //사용가능한 이메일
            duplicateResult.removeClass("validation-error");
            duplicateResult.addClass("validation-ok");
            duplicateResult.text(json.email + "은 사용 가능합니다.");
          } else {
            //사용불가능한 이메일
            duplicateResult.removeClass("validation-ok");
            duplicateResult.addClass("validation-error");
            duplicateResult.text(json.email + "은 이미 사용중 입니다.");
          }
        });
    } else {
      $(this)
        .closest(".input-div")
        .children(".validation-ok, .validation-error")
        .remove();
    }
  });

  $("#confirm-password, #password").on("keyup", function () {
    var confirmPasswordValue = $("#confirm-password").val();
    var passwordValue = $("#password").val();

    $("#password, #confirm-password")
      .closest(".input-div")
      .children(".validation-error")
      .remove();

    if (confirmPasswordValue !== passwordValue) {
      var passwordErrorMessage = $("<div>");
      passwordErrorMessage.addClass("validation-error");
      passwordErrorMessage.text("비밀번호가 일치하지 않습니다.");

      $("#password , #confirm-password").after(passwordErrorMessage);
    }
  });

  $("#show-password").on("change", function () {
    var checked = $(this).prop("checked");
    if (checked) {
      $("#password").attr("type", "text");
    } else {
      $("#password").attr("type", "password");
    }
  });

  //브라우저에서 입력값 검증하는 방법 2가지
  //1. 폼 전송시 체크
  //2. 입력폼에 값을 입력할때 체크(keyup event)

  // 회원 가입 폼이 전송이 되기 전에 입력값 재대로 작성했는지 확인
  //폼이 전송이될때 이벤트 처리
  $("#registVO").on("submit", function (event) {
    //이미 브라우저에 할당된 서브밋 콜백 이벤트를 제거한다
    event.preventDefault();

    //form 내부 존재하는 validation-error 엘리먼트를 제거
    $(this).find(".validation-error").remove();

    $("#password").trigger("keyup");

    //이름 이메일 비밀번호 중 하나 이상 재대로 입력되지 않으면 에러 메시지를 화면에 보여준다. 폼전송x
    var email = $("#email").val();
    if (!email) {
      var emailErrorMessage = $("<div>");
      emailErrorMessage.addClass("validation-error");
      emailErrorMessage.text("이메일 형태가 아닙니다.");

      $("#email").after(emailErrorMessage);
    }
    var name = $("#name").val();
    if (!name) {
      var nameErrorMessage = $("<div>");
      nameErrorMessage.addClass("validation-error");
      nameErrorMessage.text("이름을 반드시 입력해주세요");

      $("#name").after(nameErrorMessage);
    }

    var passwordPattern = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$/;
    var password = $("#password").val();
    if (!passwordPattern.test(password)) {
      var passwordErrorMessage = $("<div>");
      passwordErrorMessage.addClass("validation-error");
      passwordErrorMessage.text(
        "비밀번호는 영소문자, 영대문자, 숫자 최소 1개를 포함하여 8글자 이상으로 입력하세요",
      );

      $("#password").after(passwordErrorMessage);
    }

    //이름 이메일 비밀번호 재대로 입력시 폼을 전송
    if ($(".validation-error").length === 0) {
      //$(this).submit();  jquery event ==> 14번줄에서 이벤트가 없어져서 동작하지 않음
      this.submit(); // javascript event
    }
  });
});
