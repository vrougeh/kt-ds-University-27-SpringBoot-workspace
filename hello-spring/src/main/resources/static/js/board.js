$().ready(function () {
  //   alert("!");

  $(".attach-files").on("click", ".add-file", function () {
    //   $(".add-file").on("click", function () {  //아래 addbutton에 삭제기능 추가를 넣어야함으로(중복된 값 넣음) 해당 코드를 통하여 이벤트를 주지 않고 부모에게 이벤트를 줘서 반복 없이 실행되게 한다
    //새로운 파일이 추가될 때 마다 기존의 "add-file"을 "del-file"로 변경하고 텍스트는 +에서 -로 변경
    $(this)
      .closest(".attach-files")
      .children(".add-file")
      .removeClass("add-file")
      .addClass("del-file")
      .text("-")
      .off("click") //할당되어있는 이벤트를 제거한다
      .on("click", function () {
        //버튼왼쪽 인풋 태그 삭제 및 버튼 삭제
        $(this).prev().remove();
        $(this).remove();
      });
    var fileInput = $("<input/ >");
    fileInput.attr({
      type: "file",
      name: "attachFile",
    });

    var addButton = $("<button />");
    addButton.attr("type", "button").addClass("add-file").text("+");

    $(".attach-files").append(fileInput).append(addButton);
  });

  $(".del-file").on("click", function () {});
  
  
  $("#writeVO").on("submit",function(event){
	event.preventDefault();
	
	$(this).find(".validation-error").remove();
	
	var subject = $("#subject").val();
	if (!subject) {
	  var subjectErrorMessage = $("<div>");
	  subjectErrorMessage.addClass("validation-error");
	  subjectErrorMessage.text("제목을 입력해주세요.");

	  $("#subject").after(subjectErrorMessage);
	}else if (subject.length < 3) {
	  var subjectErrorMessage = $("<div>");
	  subjectErrorMessage.addClass("validation-error");
	  subjectErrorMessage.text("제목은 3글자 이상이여야합니다.");

	  $("#subject").after(subjectErrorMessage);
	}
	
	var email = $("#email").val();
	if (!email) {
	  var emailErrorMessage = $("<div>");
	  emailErrorMessage.addClass("validation-error");
	  emailErrorMessage.text("이메일 형태가 아닙니다.");

	  $("#email").after(emailErrorMessage);
	}
	
	if ($(".validation-error").length === 0) {
	  this.submit();
	}
	
  })
});
