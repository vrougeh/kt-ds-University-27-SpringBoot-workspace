$().ready(function () {
  var loginEmail = $(".member-info").data("email");
  var refreshReplies = function () {
    //게시글아이디
    var articleId = $(".view").data("article-id");

    fetch("/api/replies/" + articleId)
      .then(function (response) {
        return response.json();
      })
      .then(function (json) {
        console.log(json);
        var count = json.count;
        $(".replies-count").children(".count").text(count);

        var replies = json.result;
        for (var i = 0; i < replies.length; i++) {
          var reply = replies[i];

          var replyTemplate = $(".reply-item-template").html();
          //console.log(replyTemplate);
          replyTemplate = replyTemplate
            .replace("#replyId#", reply.id)
            .replace("#name#", reply.membersVO.name)
            .replace("#email#", reply.email)
            .replace("#createDate#", reply.crtDt)
            .replace("#modifyDate#", reply.mdfyDt)
            .replace("#content#", reply.reply)
            .replace("#recommendCount#", reply.recommendCnt);

          var replyDom = $(replyTemplate);

          if (!loginEmail || loginEmail === reply.email) {
            replyDom.find(".links-recommend").remove();
          }

          if (loginEmail !== reply.email) {
            replyDom.find(".links-update, .links-delete").remove();
          }

          if (!reply.fileGroupId) {
            replyDom.find(".reply-attach-files").remove();
          } else {
            replyDom
              .find(".reply-attach-files")
              .data("files", JSON.stringify(reply.files));
            //console.log("파일 있음");
            //console.log(reply.files.length)
            for (var j = 0; j < reply.files.length; j++) {
              var file = reply.files[j];
              var fileSize = file.fileLength; // bytes
              var capaType = "byte";
              if (fileSize > 1024) {
                capaType = "kb";
                fileSize = Math.ceil(fileSize / 1024);
              }
              if (fileSize > 1024) {
                capaType = "mb";
                fileSize = Math.ceil(fileSize / 1024);
              }
              //console.log(file);
              var a = $("<a>");
              a.addClass("files");
              a.text(file.displayName + " (" + fileSize + capaType + ")");
              a.attr({
                href: "/file/" + file.fileGroupId + "/" + file.fileNum,
              });
              replyDom.find(".reply-attach-files").append(a);
            }
          }

          if (!reply.mdfyDt) {
            replyDom.find(".modify-date").remove();
          }
          //TODO 댓글 추천 수정 삭제

          //댓글 추천 누르면 추천수 증가
          replyDom.find(".links-recommend").on("click", function () {
            console.log("개추");
            var replyId = $(this).closest(".reply-item").data("reply-id");
            //api 호출 endpoint : /api/replies/recommend/{댓글id}
            var target = $(this);
            console.log(target);
            fetch("/api/replies/recommend/" + replyId)
              .then(function (response) {
                return response.json();
              })
              .then(function (json) {
                //호출 결과 > {replyId:"~~", recommendCount: 1}
                console.log(json.recommendCnt);
                console.log($(this));
                //recommendCount 텍스트를 변경
                target
                  .closest(".reply-item")
                  .find(".recommend-count")
                  .text(json.recommendCnt);
              });
          });
          // 수정을 클릭하면, 댓글을 수정할 수 있는 폼이 완성된다.
          replyDom.find(".links-update").on("click", function () {
            $(".update-form").remove();

            var replyAttachFiles = $(this)
              .closest(".reply-item")
              .find(".reply-attach-files")
              .data("files");

            if (replyAttachFiles) {
              replyAttachFiles = JSON.parse(replyAttachFiles);
            }

            var content = $(this)
              .closest(".reply-item")
              .find(".content")
              .text();

            var updateTemplate = $(".reply-item-update-template").html();
            var updateFormDom = $(updateTemplate);
            updateFormDom.find("textarea").val(content);

            updateFormDom.find(".update-cancel").on("click", function () {
              $(".update-form").remove();
            });

            updateFormDom.find(".update-save").on("click", function () {
              console.log("저장버튼 클릭");
              // 수정시 필요한 데이터
              // 수정하려는 댓글의 아이디, 수정하려는 댓글의 내용, 삭제하려는 파일의 filenum, 추가하려는 파일
              var replyId = $(this).closest(".reply-item").data("reply-id");
              var updateContent = $(this)
                .closest(".update-form")
                .find("textarea")
                .val();
              var deleteFilesNum = $(this)
                .closest(".update-form")
                .find(".update-file-list")
                .find("input[type='checkbox']:checked");
              var newAttachFiles = $(this)
                .closest(".update-form")
                .find(".reply-update-attach-file")[0].files;

              var formData = new FormData();
			  formData.append("_csrf",$("meta[name='_csrf']").attr("content"));
              formData.append("content", updateContent);
              deleteFilesNum.each(function () {
                formData.append("delFileNum", $(this).val());
              });
              for (var k = 0; k < newAttachFiles.length; k++) {
                formData.append("newAttachFiles", newAttachFiles[k]);
              }

              fetch("/api/replies/update/" + replyId, {
                method: "post",
                body: formData,
              })
                .then(function (response) {
                  return response.json();
                })
                .then(function (json) {
                  $(".replies").html("");
                  refreshReplies();
                });
            });

            if (replyAttachFiles) {
              var replyItemsTemplate = $(".reply-item-update-files").html();
              for (var j = 0; j < replyAttachFiles.length; j++) {
                var replyItemFile = replyAttachFiles[j];

                var fileTemplate = replyItemsTemplate
                  .replaceAll("#fileGroupId#", replyItemFile.fileGroupId)
                  .replaceAll("#fileNum#", replyItemFile.fileNum)
                  .replaceAll("#fileDisplayName#", replyItemFile.displayName);

                updateFormDom.find(".update-file-list").append($(fileTemplate));
              }
            }

            $(this)
              .closest(".reply-item")
              .find(".content")
              .after(updateFormDom);
          });
          //댓글삭제
          replyDom.find(".links-delete").on("click", function () {
            console.log("삭제");
            // 삭제하시겠습니까 를 사용자에게 물어본다
            if (confirm("삭제하시겠습니까")) {
              var replyId = $(this).closest(".reply-item").data("reply-id");
              var target = $(this);
              // 확인을 클릭하면 삭제 api 호출 endpoint : /api/replies/delete/{댓글id}
              fetch("/api/replies/delete/" + replyId)
                .then(function (response) {
                  return response.json();
                })
                .then(function (json) {
                  // 호출 결과 > {replyId:"~~"}
                  //결과의 replyId 댓글을 목록에서 제거한다 다시불러오기x
                  target.closest(".reply-item").remove();
                });
              //취소를 클릭하면 아무일도 일어나지 않는다
            }
          });
          replyDom.css({ "margin-left": (reply.level - 1) * 2 + "rem" });
          replyDom.find(".links-write").on("click", function () {
            var replyId = $(this).closest(".reply-item").data("reply-id");
            console.log("Click! - " + replyId);

            $(".reply-form").children(".parent-reply-id").val(replyId);
            $(".reply-content").focus();
          });

          $(".replies").append($(replyDom));
        }
      });
  };
  refreshReplies();

  $(".reply-save").on("click", function () {
    var replyContent = $(".reply-content").val();
    var articleId = $(this).data("article-id");
    var parentReplyId = $(".parent-reply-id").val();

    var files = $(".reply-attach-file")[0];
    console.log(files.files[0]);

    var formData = new FormData();
    formData.append("reply", replyContent);
    formData.append("articleId", articleId);
    formData.append("parentReplyId", parentReplyId);
	formData.append("_csrf",$("meta[name='_csrf']").attr("content"));

    if (files.files.length > 0) {
      for (var i = 0; i < files.files.length; i++) {
        formData.append("attachFile", files.files[i]);
      }
    }

    fetch("/api/replies-with-file", {
      method: "post",
      body: formData,
    })
      .then(function (response) {
        return response.json();
      })
      .then(function (json) {
        console.log(json);

        $(".reply-form").children(".parent-reply-id").val("");
        $(".reply-content").val("");
        $(".replies").html("");
        refreshReplies();
      });

    console.log(replyContent, articleId, parentReplyId);
  });
});
