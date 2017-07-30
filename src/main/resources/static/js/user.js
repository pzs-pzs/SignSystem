
function g() {
    $.ajax({
        type: "POST",
        url: "/signHandler",
        success: function l ()  {
             window.location = "sign_inf.html"
        }
    })
}
var flag = true;
function f() {

    if(flag) {
        $.ajax({
            type: "POST",
            url: "/signCount",
            success: function l(data) {
                var result = JSON.parse(data);
                if (result['success'] == 1) {
                    alert("签到成功");
                }


            }
        });
        flag = false;
        return;
    }
    alert('今日已签到');


}
function check() {
    var num = document.getElementById('num').value;
    var name = document.getElementById('name').value;
    var tel = document.getElementById('tel').value;
    var email = document.getElementById('email').value;
    var password = document.getElementById('password').value;
    if(num!=''&&name!=''&&tel!=''&&email!=''&&password!=''){
        return true;
    }
    return false;
}

