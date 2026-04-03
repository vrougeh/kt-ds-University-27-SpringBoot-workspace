/**
 * 영화등록과 관련된 스크립트 작성
 */

$().ready(function () {
	$("#writeVO").on("submit",function(event){
		event.preventDefault();
		
		$(this).find(".validation-error").remove();
		
		var title = $("#title").val();
		if (!title) {
		  var titleErrorMessage = $("<div>");
		  titleErrorMessage.addClass("validation-error");
		  titleErrorMessage.text("타이틀을 반드시 입력해주세요");

		  $("#title").after(titleErrorMessage);
		}
		
		var synopsis = $("#synopsis").val();
		if (!synopsis) {
		  var synopsisErrorMessage = $("<div>");
		  synopsisErrorMessage.addClass("validation-error");
		  synopsisErrorMessage.text("시놉시스를 반드시 입력해주세요");

		  $("#synopsis").after(synopsisErrorMessage);
		}
		
		var state = $("#state").val();
		if (!state) {
		  var stateErrorMessage = $("<div>");
		  stateErrorMessage.addClass("validation-error");
		  stateErrorMessage.text("상태를 반드시 입력해주세요");

		  $("#state").after(stateErrorMessage);
		}else if(state.length > 6) {
           var stateErrorMessage = $("<div>");
           stateErrorMessage.addClass("validation-error");
           stateErrorMessage.text("5자까지 입력해주세요");
           $("#state").after(stateErrorMessage);
         }
		
		var language = $("#language").val();
		if (!language) {
		  var languageErrorMessage = $("<div>");
		  languageErrorMessage.addClass("validation-error");
		  languageErrorMessage.text("언어를 반드시 입력해주세요");

		  $("#language").after(languageErrorMessage);
		}else if (language.length > 7) {
            var languageErrorMessage = $("<div>");
            languageErrorMessage.addClass("validation-error");
            languageErrorMessage.text("6자 까지 입력해주세요");
            $("#language").after(languageErrorMessage);
        }
        
        if(!$("#runningTime").val()){
            $("#runningTime").val("0");
        }
        
        if(!$("#budget").val()){
            $("#budget").val("0");
        }
        
        if(!$("#profit").val()){
            $("#profit").val("0");
        }

		
		if ($(".validation-error").length === 0) {
	      this.submit();
	    }
		
	})
})