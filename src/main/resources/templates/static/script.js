const globalURL = "http://localhost:6969/";

window.count = 1;

function toastHTML(isError, title, msg) {
    if(isError){
        return `<div id="liveToast${count}" class="toast" role="alert" aria-live="assertive" aria-atomic="false"><div class="toast-header"><svg className="bd-placeholder-img rounded" width="20" height="20" xmlns="http://www.w3.org/2000/svg" aria-hidden="true" preserveAspectRatio="xMidYMid slice" focusable="false"><rect width="100%" height="100%" fill="#FF0000"></rect></svg><strong class="ms-2 me-auto">${title}</strong><button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button></div><div class="toast-body">${msg}</div></div>`
        // return `<div id="liveToast${count}" class="toast" role="alert" aria-live="assertive" aria-atomic="false"><div class="toast-header"><svg className="bd-placeholder-img rounded me-2" width="20" height="20" xmlns="http://www.w3.org/2000/svg" aria-hidden="true" preserveAspectRatio="xMidYMid slice" focusable="false"><rect width="100%" height="100%" fill="#FF0000"></rect></svg><strong class="me-auto">Bootstrap</strong><small>11 mins ago</small><button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button></div><div class="toast-body">Hello, world! This is a toast message.</div></div>`
    }
    return `<div id="liveToast${count}" class="toast" role="alert" aria-live="assertive" aria-atomic="false"><div class="toast-header"><svg className="bd-placeholder-img rounded" width="20" height="20" xmlns="http://www.w3.org/2000/svg" aria-hidden="true" preserveAspectRatio="xMidYMid slice" focusable="false"><rect width="100%" height="100%" fill="#007aff"></rect></svg><strong class="ms-2 me-auto">${title}</strong><button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button></div><div class="toast-body">${msg}</div></div>`;
    // return `<div id="liveToast${count}" class="toast" role="alert" aria-live="assertive" aria-atomic="false"><div class="toast-header"><svg className="bd-placeholder-img rounded me-2" width="20" height="20" xmlns="http://www.w3.org/2000/svg" aria-hidden="true" preserveAspectRatio="xMidYMid slice" focusable="false"><rect width="100%" height="100%" fill="#007aff"></rect></svg><strong class="me-auto">Bootstrap</strong><small>11 mins ago</small><button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button></div><div class="toast-body">Hello, world! This is a toast message.</div></div>`;
}

//FF0000

//reference - https://www.w3schools.com/js/js_cookies.asp
function getCookie(cname) {
    let name = cname + "=";
    let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    for(let i = 0; i <ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function setCookie(name, value) {
    const d = new Date();
    d.setTime(d.getTime() + (7*24*60*60*1000));
    let expires = "expires="+ d.toUTCString();
    document.cookie = name + "=" + value + ";" + expires + ";path=/";
}

$(document).ready(function () {
    $('#navDiv').load('./nav.html');

    $(".nav-link").click(function (){
        $(".nav-link").removeClass("active");
        $(this).addClass("active");
        console.log(this)
    });

    $(".nav-item").click(function (){
        // $(".nav-link").removeClass("active");
        // $(this).addClass("active");
        console.log(this)
    });

    $.ajax({
        url: globalURL + "jwt/validateJWTToken",
        type: "POST",
        dataType: "json",
        data: getCookie("token")
    })
    .done(function(response) {
        try {
            if(data.isError){
                addToast(true, "Error", data.msg);
                window.location.href="./index.html";
            } else {
                setCookie("id", data.data.obj.id);
                if (data.data.type == 'patient'){
                    setCookie("name", data.data.obj.patientName);
                    setCookie("email", data.data.obj.patientEmail);
                    setCookie("city", data.data.obj.patientAddressCity);
                    setCookie("province", data.data.obj.patientAddressProvince);
                    setCookie("type", "Patient");
                } else {
                    setCookie("name", data.data.obj.doctorName);
                    setCookie("email", data.data.obj.doctorEmail);
                    setCookie("city", data.data.obj.doctorAddressCity);
                    setCookie("province", data.data.obj.doctorAddressProvince);
                    setCookie("type", "Doctor");
                }
            }
        } catch(err){
            addToast(true, "Error", "Some unknown error occurred. Pls try again later!")
            window.location.href="./index.html";
        }
    })
    .fail(function (jqXHR, textStatus, errorThrown){
        addToast(true, "Error", "Pls log in again!");
        window.location.href="./index.html";
    });
})