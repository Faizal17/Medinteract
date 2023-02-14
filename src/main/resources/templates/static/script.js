const globalURL = "http://localhost:6969/";


function toastHTML(isError, count) {
    if(isError){
        return `<div id="liveToast${count}" class="toast" role="alert" aria-live="assertive" aria-atomic="false"><div class="toast-header"><svg className="bd-placeholder-img rounded me-2" width="20" height="20" xmlns="http://www.w3.org/2000/svg" aria-hidden="true" preserveAspectRatio="xMidYMid slice" focusable="false"><rect width="100%" height="100%" fill="#007aff"></rect></svg><strong class="me-auto">Bootstrap</strong><small>11 mins ago</small><button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button></div><div class="toast-body">Hello, world! This is a toast message.</div></div>`
    }
    return `<div id="liveToast${count}" class="toast" role="alert" aria-live="assertive" aria-atomic="false"><div class="toast-header"><svg className="bd-placeholder-img rounded me-2" width="20" height="20" xmlns="http://www.w3.org/2000/svg" aria-hidden="true" preserveAspectRatio="xMidYMid slice" focusable="false"><rect width="100%" height="100%" fill="#007aff"></rect></svg><strong class="me-auto">Bootstrap</strong><small>11 mins ago</small><button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button></div><div class="toast-body">Hello, world! This is a toast message.</div></div>`;
}

//FF0000