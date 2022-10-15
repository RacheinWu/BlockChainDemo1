"use strict";

$(function () {
  getAll();
  $(document).on("click", ".col", function () {
    var x = $(this);
    var checkbox = x.children(".col-check-content"); // alert(checkbox.prop("checked"))

    if (checkbox.prop("checked") === false) {
      var index = x.children(".block").text();
      $.ajax({
        async: true,
        type: "GET",
        url: "http://localhost:8875/api/block/" + index,
        // contentType: "application/json;charset=UTF-8",
        // data:JSON.stringify(requestBody),
        dataType: "json",
        success: function success(data) {
          if (data.code === 200) {
            // alert(data)
            x.after(" <div class=\"content\">\n" + "                        <div class=\"pH prefix\">\n" + "                            <div class=\"f1\">Hash:</div>\n" + "                            <div class=\"value\">" + data.data.hash + "</div>\n" + "                            <div class=\"check-button button\" style=\"margin-left: auto\">\n" + "                                <svg style=\"color: #ffffff\" xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" fill=\"currentColor\" class=\"bi bi-check-circle-fill\" viewBox=\"0 0 16 16\">\n" + "                                    <path d=\"M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z\"/>\n" + "                                </svg>\n" + "                                校验有效性</div>\n" + "                        </div>\n" + "                        <div class=\"pH prefix\">\n" + "                            <div class=\"f1\">previousHash:</div>\n" + "                            <div class=\"value\">" + data.data.previousHash + "</div>\n" + "                        </div>\n" + "                        <div class=\"pH prefix\">\n" + "                            <div class=\"f1\">区块号:</div>\n" + "                            <div class=\"value\">" + data.data.id + "</div>\n" + "                        </div>\n" + "                        <div class=\"pH prefix\">\n" + "                            <div class=\"f1\">创建时间:</div>\n" + "                            <div class=\"value\">" + stampToDate(data.data.timestamp) + "</div>\n" + "                        </div>\n" + "                        <div class=\"pH prefix\">\n" + "                            <div class=\"f1\">最新一次手动校验时间:</div>\n" + "                            <div class=\"value\">" + stampToDate(data.data.checkTime) + "- " + ch2(data.data.lastCheckStatus) + "</div>\n" + "                        </div>\n" + "                        <div class=\"pH prefix\">\n" + "                            <div class=\"f1\">数据类型</div>\n" + "                            <div class=\"value\">" + data.data.type + "</div>\n" + "                        </div>\n" + "                        <div class=\"pH prefix\">\n" + "                            <div class=\"f1\">data:</div>\n" + "                            <div class=\"value\">\n" + "                                <div class=\"data-box\">\n" + data.data.data + "                                </div>");
          }
        }
      });
      checkbox.prop("checked", true);
    } else {// checkbox.prop("checked", false);
    }
  });
  var hash = null;
  $(document).on("click", ".check-button", function () {
    $(".func-box-1").css("display", "block");
    $(".func-box-1 .c-2 ").css("display", "block"); //获取区块hash：

    hash = $(this).prev(".value").text();
    alert(hash);
  }); //queding:

  $(document).on("click", ".qued", function () {
    $(".func-box-1").css("display", "none");
    $(".c-3").css("display", "none");
    $(".loading-i").css("display", "block");
    $(".result").text("结果为：");
  }); //No:

  $(document).on("click", ".func-box-1 .c-2 .n", function () {
    $(".func-box-1").css("display", "none");
  }); // 进行校验：

  $(document).on("click", ".func-box-1 .c-2 .y", function () {
    $(".func-box-1 .c-2 ").css("display", "none");
    $(".func-box-1 .c-3 ").css("display", "block");
    $(".news ").css("display", "block"); //接口：

    $.ajax({
      async: true,
      type: "GET",
      url: "http://localhost:8875/api/block/calc/" + hash,
      // contentType: "application/json;charset=UTF-8",
      // data:JSON.stringify(requestBody),
      dataType: "json",
      success: function success(data) {
        if (data.code === 200) {
          //转1s：
          $(".news ").css("display", "none");
          $("body").oneTime('1s', function () {
            // alert("???")
            $(".news ").css("display", "block");
            $(".c-3 .title").css("border-bottom", "3px solid green");
            $(".c-3 .title").text("校验完成");
            $(".loading-i").css("display", "none");
            $(".news .content").text("区块有效！");
            $(".result").text("结果为：有效"); // $(".result").css("border-top", "3px solid green");
          });
        }
      }
    });
  });
});

function ch2(i) {
  if (i === 1) return "<span style='color: #05c405'>有效</span>";else return "<span style='color: red'>无效</span>";
}

function getAll() {
  $.ajax({
    async: true,
    type: "GET",
    url: "http://localhost:8875/api/block/all",
    // contentType: "application/json;charset=UTF-8",
    // data:JSON.stringify(requestBody),
    dataType: "json",
    success: function success(data) {
      if (data.code === 200) {
        var arrays = data.data;
        var length = arrays.length; // $("#table-body").html(" ");

        for (var i = 0; i < length; i++) {
          $("#table-body").append("<div class=\"col\", id=\"col-" + (i + 2) + "\">\n" + "<input class=\"col-check-content\" type=\"checkbox\">" + "                        <div class=\"PreviousHash  xb\">" + arrays[i].previousHash.substring(0, 19) + "...</div>\n" + "                        <div class=\"block xb\">" + arrays[i].id + "</div>\n" + "                        <div class=\"age\">" + stampToDate(arrays[i].timestamp) + "</div>\n" + "                        <div class=\"Hash\">" + arrays[i].hash.substring(0, 19) + "...</div>\n" + "                        <div class=\"data-type xb\">" + arrays[i].type + "</div>\n" + "                        <div class=\"data\">{\"\"DAWDAWDAW\"DAWD\"...}</div>\n" + "                    </div>");
        }
      }
    }
  });
}

function getOneInfo(index) {
  $.ajax({
    async: true,
    type: "GET",
    url: "http://localhost:8875/api/block/" + index,
    // contentType: "application/json;charset=UTF-8",
    // data:JSON.stringify(requestBody),
    dataType: "json",
    success: function success(data) {
      if (data.code === 200) {
        // alert(data)
        data = data.data;
        return data.data;
      }
    }
  });
}
/**
 * 将时间戳转为时间
 * @param {string}
 * @returns {string}
 */


function stampToDate(time) {
  var date = new Date(Number(time)); //将接收到的的String类型的时间转为数字类型

  var y = date.getFullYear();
  var m = date.getMonth() + 1;
  var d = date.getDate();
  var hour = date.getHours().toString();
  var minutes = date.getMinutes().toString();
  var seconds = date.getSeconds().toString();

  if (hour < 10) {
    hour = "0" + hour;
  }

  if (minutes < 10) {
    minutes = "0" + minutes;
  }

  if (seconds < 10) {
    seconds = "0" + seconds;
  }

  return y + '-' + (m < 10 ? '0' + m : m) + '-' + (d < 10 ? '0' + d : d) + " " + hour + ":" + minutes + ":" + seconds;
}