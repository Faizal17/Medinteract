//const globalURL = "http://localhost:6969/";

function showDoctorList(tempResponceData, doctorList, avgRating) {
  //console.log(doctorList)
  //console.log("........."+tempResponceData.doctorAddressCity+JSON.stringify(tempResponceData));
  //console.log(avgRating);
  let imagePath = "static/images/doctor/";


  let htmlString = `
  <div class="card mx-auto text-center shadow" id="doctor_list_div_subdiv_card">
    <div style="height:30%;">
      <img class="rounded-circle mx-auto" src="${imagePath}${tempResponceData.id}.jpg" style="height: 100%; width: 30%;"
        onError="this.onerror=null;this.src='${imagePath}default.jpg';">
    </div>
    <div class="card-body">
        <ul class="list-group listGroupBorder">
            <li class="list-group-item listGroupBorder"><h5 class="card-title">`+ tempResponceData.doctorName + `</h5></li>
            <li class="list-group-item listGroupBorder"><i class="bi bi-envelope-fill"></i> <a href="mailto:`+ tempResponceData.doctorEmail + `" class="card-link">` + tempResponceData.doctorEmail + `</a></li>
            <li class="list-group-item listGroupBorder"><i class="bi bi-geo-alt-fill"></i> `+ tempResponceData.doctorAddressProvince + `,` + tempResponceData.doctorAddressCity + `</li>
            <li class="list-group-item listGroupBorder"><i class="bi bi-mortarboard-fill"></i> `+ tempResponceData.doctorQualification + `</li>
        </ul>

        <div class="row">
            <div class="rating col">
                <span class="text-muted fw-light fs-1"><b>${avgRating}</b>
                    <spam class="fs-5">/5</spam>
                </span>
                <div class="rating-stars">
                    <span><i class="bi bi-star" style="color:orange" id="star1_${tempResponceData.id}"></i></span>
                    <span><i class="bi bi-star" style="color:orange" id="star2_${tempResponceData.id}"></i></span>
                    <span><i class="bi bi-star" style="color:orange" id="star3_${tempResponceData.id}"></i></span>
                    <span><i class="bi bi-star" style="color:orange" id="star4_${tempResponceData.id}"></i></span>
                    <span><i class="bi bi-star" style="color:orange" id="star5_${tempResponceData.id}"></i></span>
                </div>
            </div>
            
        </div>

        <div class="row ">
            <div class="col-6">
                <button type="button" class="btn btn-outline-warning btn-sm" style="width: 100%; color:black"
                    data-bs-toggle="modal" data-bs-target="#comments_modal"
                    onclick="loadComents(${tempResponceData.id})" style="width: 10rem;">Feedback</button>
            </div>
            <div class="col-6">
                <button id="${tempResponceData.id}_${tempResponceData.doctorName}"
                    class="btn btn-primary btn-sm float-end calendar" style="width: 100%;">Book</button>
            </div>
        </div>
    </div>
  </div>
  <br>

  `;

  let div = document.createElement("div");
  div.id = "doctor_list_div_subdiv";
  div.classList.add("col-md-4");
  div.classList.add("col-sm-6");
  //div.classList.add("col-12");
  div.classList.add("mb-4");
  //div.classList.add("card");
  //div.classList.add("mx-auto");
  //div.classList.add("shadow");
  div.classList.add("doctorListDivCard");
  div.innerHTML = htmlString;

  doctorList.appendChild(div);
  //doctorList.appendChild(document.createElement("br"))

  // let divEmpty = document.createElement("div");
  // divEmpty.classList.add("col-sm-1");

  // doctorList.appendChild(divEmpty);

  let j = 1;
  let starHtml;
  for (; j <= avgRating; j++) {
    starHtml = document.getElementById("star" + j + `_` + tempResponceData.id);
    starHtml.classList.value = "bi bi-star-fill";
  }
  if (j - 1 < avgRating) {
    starHtml = document.getElementById("star" + j + `_` + tempResponceData.id);
    starHtml.classList.value = "bi bi-star-half";
  }

}

function searchdoctor(event) {


  //console.log("In searchDoctor ");
  //event.preventDefault();
  let apiUrl;
  let data;
  //const id = this.id;

  apiUrl = "doctor/get_doctor_on_details_and_city";
  data = {
    "doctorName": document.getElementById("doctor_search_form_name").value,
    "doctorAddressProvince": document.getElementById("doctor_search_form_province").value,
    "doctorAddressCity": document.getElementById("doctor_search_form_city").value,
    "doctorQualification": document.getElementById("doctor_search_form_qualification").value
  }

  //console.log("checking data\n" + JSON.stringify(data));

  let responseData;
  let doctorList = document.getElementById("doctor_list_div");

  $.ajax({
    url: globalURL + apiUrl,
    type: "POST",
    dataType: "json",
    async: false,
    contentType: "application/json",
    data: JSON.stringify(data),

  }).done(function (response) {

    try {
      responseData = response;

      if (responseData.isError) {
        addToast(true, "Error", responseData.msg);
        return false;
      } else {

        //addToast(false, "Success", "Doctors featched successfully!")
      }
    } catch (err) {
      //addToast(true, "Error", "Some unknown error occurred. Unable to featch Doctors!" + err)
      return false;
    }
  })
    .fail(function (jqXHR, textStatus, errorThrown) {
      //addToast(true, "Error", "Some unknown error occurred. Unable to featch Doctors!");
      return false;
    });;


  let avgRatingList;

  $.ajax({
    url: globalURL + "feedback/fetchAvgFeedback",

    type: "GET",
    async: false,
    dataType: "json",
    contentType: "application/json"
  }).done(function (responseList) {
    try {
      avgRatingList = responseList;


      if (avgRatingList.isError) {
        addToast(true, "Error", avgRatingList.msg);
        return false;
      } else {

        //addToast(false, "Success", "Doctors featched successfully!")
      }
    } catch (err) {
      //addToast(true, "Error", "Some unknown error occurred. Unable to featch Doctors!" + err)
      return false;
    }
  })
    .fail(function (jqXHR, textStatus, errorThrown) {
      //addToast(true, "Error", "Some unknown error occurred. Unable to featch Doctors!");
      return false;
    });;


  console.log(avgRatingList);
  doctorList.innerHTML = "";

  let tempResponceData;
  let tempAvgRating;
  let tempDocAvgRating;
  let DocAvgRating;
  for (let i = 0; i < responseData.data.length; i++) {

    tempResponceData = responseData.data[i];

    //console.log(tempResponceData.id);
    tempAvgRating = avgRatingList.data;
    tempDocAvgRating = tempAvgRating.filter(obj => obj.doctorId == tempResponceData.id);
    if (tempDocAvgRating.length > 0) {
      DocAvgRating = tempDocAvgRating[0].avgRating;
      DocAvgRating = DocAvgRating.toFixed(1);
    }
    else {
      DocAvgRating = "-";
    }

    //console.log(tempDocAvgRating, tempResponceData.id);

    showDoctorList(tempResponceData, doctorList, DocAvgRating);

  }

}

$(document).ready(function () {
  const doctorList = document.getElementById("doctor_list_div");
  let responseData;



  console.log("In document ready..")

  searchdoctor();
  const patientId = getCookie('id');
  loadAppointmentsDashboard(patientId);

});

function selectProvince(province) {
  //console.log("In the selectProvince" + "\n" + provinceList.get(province));
  let citySel = document.getElementById("doctor_search_form_city");
  citySel.length = 1;
  let cities = provinceList.get(province)["data"];
  console.log(cities, province, provinceList)
  for (let i = 0; i < cities.length; i++) {
    $('#doctor_search_form_city').append(`<option value="${cities[i]['id']}">${cities[i]['city']}</option>`);
  }
}


function loadComents(doctorId) {
  const patientId = getCookie('id');
  const patientName = getCookie('name');

  let data = {
    "patientId": patientId,
    "doctorId": doctorId
  }

  let apiUrl = "feedback/fetchFeedback_by_doctorId_and_patient"
  let responseData;

  $.ajax({
    url: globalURL + apiUrl,
    type: "POST",
    dataType: "json",
    async: false,
    contentType: "application/json",
    data: JSON.stringify(data),

  }).done(function (response) {

    try {
      responseData = response;

      if (responseData.isError) {
        addToast(true, "Error", responseData.msg);
        return false;
      } else {
        //console.log(responseData);
        //addToast(false, "Success", "Doctors featched successfully!")
      }
    } catch (err) {
      //addToast(true, "Error", "Some unknown error occurred. Unable to featch Doctors!" + err)
      return false;
    }
  })
    .fail(function (jqXHR, textStatus, errorThrown) {
      //addToast(true, "Error", "Some unknown error occurred. Unable to featch Doctors!");
      return false;
    });;


  let commentFlag = false;
  let rating = 0;
  if (typeof responseData.data[0].comment === 'undefined') {
    responseData.data[0].comment = "";
    responseData.data[0].id = -1;
    commentFlag = true;
  }
  else {
    rating = responseData.data[0].rating;
  }

  let commentList = document.getElementById("comments_modal_body");
  let imagePath = "static/images/patient/"

  let htmlString = `<div class="bg-light p-2">
                      <div class="d-flex flex-row-end">
                      <div class="col align-self-end">
                      <img class="rounded-circle" src="${imagePath}${patientId}.jpg" height="40" width="40" onError="this.onerror=null;this.src='${imagePath}default.jpg';">           
                      <a href="#" class="text-decoration-none" disabled>${patientName} (You)</a>
                      </div>
                      
                      <div class="rating col float-end">
                      <div class="rating-stars float-end">
                        <span><i class="1 bi bi-star patientFeedbackStar" data-my-info="${rating}" id="star1_${patientId}_${doctorId}" onclick="setStar(1, ${patientId}, ${doctorId}, ${responseData.data[0].id})"></i></span>
                        <span><i class="2 bi bi-star patientFeedbackStar" data-my-info="${rating}" id="star2_${patientId}_${doctorId}" onclick="setStar(2, ${patientId}, ${doctorId}, ${responseData.data[0].id})"></i></span>
                        <span><i class="3 bi bi-star patientFeedbackStar" data-my-info="${rating}" id="star3_${patientId}_${doctorId}" onclick="setStar(3, ${patientId}, ${doctorId}, ${responseData.data[0].id})"></i></span>
                        <span><i class="4 bi bi-star patientFeedbackStar" data-my-info="${rating}" id="star4_${patientId}_${doctorId}" onclick="setStar(4, ${patientId}, ${doctorId}, ${responseData.data[0].id})"></i></span>
                        <span><i class="5 bi bi-star patientFeedbackStar" data-my-info="${rating}" id="star5_${patientId}_${doctorId}" onclick="setStar(5, ${patientId}, ${doctorId}, ${responseData.data[0].id})"></i></span>
                      </div>
                      </div>
                      </div>
                      <div class="d-flex flex-row align-items-start">
                      <textarea class="form-control ml-1 shadow-none textarea" id="textCommentArea_${doctorId}">${responseData.data[0].comment}</textarea></div>
                      <div class="mt-2 text-right float-end"><button class="btn btn-primary btn-sm shadow-none" id="postButton_${doctorId}" type="button" onclick="saveComment(${doctorId}, ${responseData.data[0].id})" >Post Comment</button></div>
                    </div><br><br>
                    <div id="othersCommentBoxDiv" class="scrollable">
                    `;

  let tempResponceData;
  let feedbackDate;
  let dateOptions;
  let formattedFeedbackDate;
  let TimeOptions;
  let formattedFeedbackTime;
  for (let i = 1; i < responseData.data.length; i++) {

    tempResponceData = responseData.data[i];
    feedbackDate = new Date(tempResponceData.feedbackDate);
    dateOptions = { year: 'numeric', month: 'long', day: 'numeric' };
    formattedFeedbackDate = feedbackDate.toLocaleDateString('en-US', dateOptions);
    TimeOptions = { hour: 'numeric', minute: 'numeric', hour12: true };
    formattedFeedbackTime = feedbackDate.toLocaleTimeString('en-US', TimeOptions);


    htmlString = htmlString + `
    <div class="d-flex flex-row-end align-items-end">
    <div><img class="rounded-circle" src="${imagePath}${tempResponceData.id}.jpg" height="40" width="40" onError="this.onerror=null;this.src='${imagePath}default.jpg';">           
    <a href="#" class="text-decoration-none" disabled>${tempResponceData.patientName}</a></div>
    <div class="col align-text-bottom"><p class="fw-light fs-6 col align-text-bottom text-end mb-0">${formattedFeedbackDate} ${formattedFeedbackTime}</p></div>
    </div>
    <div class="input-group mb-3">
    <span class="input-group-text" id="rating">${tempResponceData.rating} <i class="bi bi-star" id="comment_start_${tempResponceData.id}"></i></span>
    <span class="form-control" ><p  id="rating">${tempResponceData.comment}</p></span>
    </div>`
  }

  htmlString = htmlString + "</div>";

  //<input type="text" class="form-control" value="${tempResponceData.comment}" aria-label="Username" aria-describedby="basic-addon1" disabled>


  commentList.innerHTML = "";
  let div = document.createElement("div");
  div.id = "comment_id";
  div.innerHTML = htmlString;
  commentList.appendChild(div);
  commentList.appendChild(document.createElement("br"))

  let patientComment = document.getElementById("textCommentArea_" + doctorId);
  let postButton = document.getElementById("postButton_" + doctorId);

  if (!commentFlag) {
    postButton.textContent = "Edit Comment";
    patientComment.setAttribute("disabled", "");

  }

  console.log(rating);
  let currentPatientRating;
  for (let i = 1; i <= rating; i++) {
    console.log("In setting star for crueent user comment");
    currentPatientRating = document.getElementById("star" + i + "_" + patientId + "_" + doctorId);
    currentPatientRating.classList.remove("bi-star");
    currentPatientRating.classList.add("bi-star-fill");
  }

  const patientFeedbackStar = document.querySelectorAll(".patientFeedbackStar");

  patientFeedbackStar.forEach(function (element) {
    element.addEventListener("mouseenter", function () {
      this.classList.remove("bi-star");
      this.classList.add("bi-star-fill");
      let currentStar = this.classList[0];
      let filledTill = this.dataset.myInfo;

      let tempCurrentStar;
      let i;
      for (i = 1; i <= currentStar; i++) {
        tempCurrentStar = document.getElementById("star" + i + "_" + patientId + "_" + doctorId);
        tempCurrentStar.classList.remove("bi-star");
        tempCurrentStar.classList.add("bi-star-fill");
      }
      for (; i <= 5; i++) {
        tempCurrentStar = document.getElementById("star" + i + "_" + patientId + "_" + doctorId);
        tempCurrentStar.classList.remove("bi-star-fill");
        tempCurrentStar.classList.add("bi-star");
      }


    });

    element.addEventListener("mouseleave", function () {
      this.classList.remove("bi-star-fill");
      this.classList.add("bi-star");
      let currentStar = this.dataset.myInfo;
      let tempCurrentStar;
      let i;
      for (i = 1; i <= currentStar; i++) {
        tempCurrentStar = document.getElementById("star" + i + "_" + patientId + "_" + doctorId);
        tempCurrentStar.classList.remove("bi-star");
        tempCurrentStar.classList.add("bi-star-fill");
      }
      for (; i <= 5; i++) {
        tempCurrentStar = document.getElementById("star" + i + "_" + patientId + "_" + doctorId);
        tempCurrentStar.classList.remove("bi-star-fill");
        tempCurrentStar.classList.add("bi-star");
      }

    });
  });


}


function saveComment(doctorId, feedbackId) {
  const patientId = getCookie('id');
  let textCommentArea = document.getElementById("textCommentArea_" + doctorId);
  let postButton = document.getElementById("postButton_" + doctorId);
  let rating = document.getElementById("star1_" + patientId + "_" + doctorId).dataset.myInfo;

  //console.log(javaDate, localDate);

  let data = {
    "patientId": patientId,
    "doctorId": doctorId,
    "comment": textCommentArea.value,
    "rating": rating
  }



  if (postButton.textContent == "Edit Comment") {
    postButton.textContent = "Update Comment";
    textCommentArea.removeAttribute("disabled");
    return;
  }
  else if (postButton.textContent == "Update Comment") {
    data.id = feedbackId;
  }

  console.log(data);

  $.ajax({
    url: globalURL + "feedback/saveFeedback",
    type: "POST",
    dataType: "json",
    async: false,
    contentType: "application/json",
    data: JSON.stringify(data),

  }).done(function (response) {

    try {
      responseData = response;

      if (responseData.isError) {
        addToast(true, "Error", responseData.msg);
        return false;
      } else {
        console.log(responseData);
        //addToast(false, "Success", "Doctors featched successfully!")
      }
    } catch (err) {
      //addToast(true, "Error", "Some unknown error occurred. Unable to featch Doctors!" + err)
      return false;
    }
  })
    .fail(function (jqXHR, textStatus, errorThrown) {
      //addToast(true, "Error", "Some unknown error occurred. Unable to featch Doctors!");
      return false;
    });;

  postButton.textContent = "Edit Comment";
  textCommentArea.setAttribute("disabled", "");
  loadComents(doctorId);
  searchdoctor();

}


function setStar(currentStar, patientId, doctorId, feedbackId) {
  console.log("In setSatr")
  let i;
  let tempCurrentStar;
  for (i = 1; i <= currentStar; i++) {
    tempCurrentStar = document.getElementById("star" + i + "_" + patientId + "_" + doctorId);
    tempCurrentStar.classList.remove("bi-star");
    tempCurrentStar.classList.add("bi-star-fill");
    tempCurrentStar.dataset.myInfo = currentStar;
  }
  for (; i <= 5; i++) {
    tempCurrentStar = document.getElementById("star" + i + "_" + patientId + "_" + doctorId);
    tempCurrentStar.classList.remove("bi-star-fill");
    tempCurrentStar.classList.add("bi-star");
    tempCurrentStar.dataset.myInfo = currentStar;
  }

  saveComment(doctorId, feedbackId);

}

function loadAppointmentsDashboard(patientId) {

  let data = {
    "patientId": patientId
  }

  let responseData;

  $.ajax({
    url: globalURL + "appointment/fetchAppointmentsByPatientAfterDate",
    type: "POST",
    dataType: "json",
    async: false,
    contentType: "application/json",
    data: JSON.stringify(data),

  }).done(function (response) {

    try {
      responseData = response;

      if (responseData.isError) {
        addToast(true, "Error", responseData.msg);
        return false;
      } else {
        console.log(responseData);
        //addToast(false, "Success", "Doctors featched successfully!")
      }
    } catch (err) {
      //addToast(true, "Error", "Some unknown error occurred. Unable to featch Doctors!" + err)
      return false;
    }
  })
    .fail(function (jqXHR, textStatus, errorThrown) {
      //addToast(true, "Error", "Some unknown error occurred. Unable to featch Doctors!");
      return false;
    });;

  let i;
  let todayDate = new Date();
  let todayTextCommentArea = document.getElementById("todayAppoinmentList");
  let laterTextCommentArea = document.getElementById("laterAppoinmentList");
  let todayHtmlString = `<ul class="list-group list-group-flush">`
  let laterHtmlString = `<ul class="list-group list-group-flush">`
  let todayHaveAppoinments = false;
  let laterHaveAppoinments = true;
  let laterAppoinmentCount = 2;
  let todayAppoinmentCount = 3;

  for (i = 0; i < responseData.data.length; i++) {
    tempResponceData = responseData.data[i];
    tempDate = new Date(tempResponceData.startTime);
    if (tempDate.getDate() === todayDate.getDate() && tempDate.getMonth() === todayDate.getMonth() && tempDate.getFullYear() === todayDate.getFullYear()) {
      if (todayAppoinmentCount > 0) {
        todayHaveAppoinments = true;
        todayHtmlString = todayHtmlString + ` <li class="list-group-item"><a  href="./appointments.html"><i class="bi bi-clock"></i> ${tempDate.getHours()}h:${tempDate.getMinutes()}m</a ></li > `;
        todayAppoinmentCount--;
      }

    }
    else {
      if (laterAppoinmentCount > 0) {
        laterHaveAppoinments = true;
        laterHtmlString = laterHtmlString + ` <li class="list-group-item"><a  href="./appointments.html"><i class="bi bi-calendar4-week"></i> ${tempDate.getMonth()}/${tempDate.getDay()}/${tempDate.getFullYear()}</a ></li > `;
        laterAppoinmentCount--;
      }
    }
  }

  todayHtmlString = todayHtmlString + `</ul > `;
  laterHtmlString = laterHtmlString + `</ul > `;

  if (todayHaveAppoinments) {
    todayTextCommentArea.innerHTML = todayHtmlString;
  }
  if (laterTextCommentArea) {
    laterTextCommentArea.innerHTML = laterHtmlString;
  }


}