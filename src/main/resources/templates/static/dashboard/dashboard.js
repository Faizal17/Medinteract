//const globalURL = "http://localhost:6969/";

function showDoctorList(tempResponceData, doctorList, avgRating) {
  //console.log(doctorList)
  //console.log("........."+tempResponceData.doctorAddressCity+JSON.stringify(tempResponceData));
  //console.log(avgRating);


  let htmlString = `<div class="card col-md-6 mx-auto"  id="doctor_list_div_card" style="width: 35rem;">
          <div class="card-body">
            <h5 class="card-title">`+ tempResponceData.doctorName + `</h5>
            <p class="card-text">Dr. `+ tempResponceData.doctorName + ` is a ` + tempResponceData.doctorType + ` who provieds their services in ` + tempResponceData.doctorAddressCity + `</p>
            <div class="card-body">
              <ul class="list-group ">
                <li class="list-group-item">Email: <a href="mailto:`+ tempResponceData.doctorEmail + `" class="card-link">` + tempResponceData.doctorEmail + `</a></li>
                <li class="list-group-item">From: `+ tempResponceData.doctorAddressProvince + `,` + tempResponceData.doctorAddressCity + `</li>
                <li class="list-group-item">Qualifications: `+ tempResponceData.doctorQualification + `</li>
              </ul>
            </div>
          
            <div class="row">
            <div class="rating col">
            <span class="text-muted fw-light fs-1"><b>${avgRating}</b>/5</span>
              <div class="rating-stars">
                <span><i class="bi bi-star" style="color:orange" id="star1_${tempResponceData.id}"></i></span>
                <span><i class="bi bi-star" style="color:orange" id="star2_${tempResponceData.id}"></i></span>
                <span><i class="bi bi-star" style="color:orange" id="star3_${tempResponceData.id}"></i></span>
                <span><i class="bi bi-star" style="color:orange" id="star4_${tempResponceData.id}"></i></span>
                <span><i class="bi bi-star" style="color:orange" id="star5_${tempResponceData.id}"></i></span>
              </div>
            </div>
            <div class="col">
            
            <button id="${tempResponceData.id}_${tempResponceData.doctorName}" class="btn btn-primary float-end calendar" style="width: 10rem;">Book a Appointment</button>
            </div>
            </div>
            <button class="btn btn-primary" type="button"  style="width: 10rem;" data-toggle="collapse" data-target="#comments_${tempResponceData.id}" aria-expanded="false" onclick="loadComents(${tempResponceData.id})" style="width: 10rem;">
              Feedback
            </button>
            
            <br><br>

            <div class="collapse w-100" id="comments_${tempResponceData.id}">
              <div class="card card-body" id="comments_body_${tempResponceData.id}">
                
              </div>
            </div>

          </div>

          
        </div>
        
       
        `;

  let div = document.createElement("div");
  div.id = "doctor_list_div_subdiv";
  div.innerHTML = htmlString;

  doctorList.appendChild(div);
  doctorList.appendChild(document.createElement("br"))

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

        addToast(false, "Success", "Doctors featched successfully!")
      }
    } catch (err) {
      addToast(true, "Error", "Some unknown error occurred. Unable to featch Doctors!" + err)
      return false;
    }
  })
    .fail(function (jqXHR, textStatus, errorThrown) {
      addToast(true, "Error", "Some unknown error occurred. Unable to featch Doctors!");
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

        addToast(false, "Success", "Doctors featched successfully!")
      }
    } catch (err) {
      addToast(true, "Error", "Some unknown error occurred. Unable to featch Doctors!" + err)
      return false;
    }
  })
    .fail(function (jqXHR, textStatus, errorThrown) {
      addToast(true, "Error", "Some unknown error occurred. Unable to featch Doctors!");
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
        addToast(false, "Success", "Doctors featched successfully!")
      }
    } catch (err) {
      addToast(true, "Error", "Some unknown error occurred. Unable to featch Doctors!" + err)
      return false;
    }
  })
    .fail(function (jqXHR, textStatus, errorThrown) {
      addToast(true, "Error", "Some unknown error occurred. Unable to featch Doctors!");
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

  let commentList = document.getElementById("comments_body_" + doctorId);

  let htmlString = `<div class="bg-light p-2">
                      <div class="d-flex flex-row-end">
                      <div class="col align-self-end"><a href="#" class="text-decoration-none" disabled>${patientName} (You)</a></div>
                      
                      <div class="rating col float-end">
                      <div class="rating-stars float-end">
                        <span><i class="1 bi bi-star patientFeedbackStar" data-my-info="0" id="star1_${patientId}_${doctorId}" onclick="setStar(1, ${patientId}, ${doctorId})"></i></span>
                        <span><i class="2 bi bi-star patientFeedbackStar" data-my-info="0" id="star2_${patientId}_${doctorId}" onclick="setStar(2, ${patientId}, ${doctorId})"></i></span>
                        <span><i class="3 bi bi-star patientFeedbackStar" data-my-info="0" id="star3_${patientId}_${doctorId}" onclick="setStar(3, ${patientId}, ${doctorId})"></i></span>
                        <span><i class="4 bi bi-star patientFeedbackStar" data-my-info="0" id="star4_${patientId}_${doctorId}" onclick="setStar(4, ${patientId}, ${doctorId})"></i></span>
                        <span><i class="5 bi bi-star patientFeedbackStar" data-my-info="0" id="star5_${patientId}_${doctorId}" onclick="setStar(5, ${patientId}, ${doctorId})"></i></span>
                      </div>
                      </div>
                      </div>
                      <div class="d-flex flex-row align-items-start">
                      <textarea class="form-control ml-1 shadow-none textarea" id="textCommentArea_${doctorId}">${responseData.data[0].comment}</textarea></div>
                      <div class="mt-2 text-right float-end"><button class="btn btn-primary btn-sm shadow-none" id="postButton_${doctorId}" type="button" onclick="saveComment(${doctorId}, ${responseData.data[0].id})" >Post Comment</button></div>
                    </div><br><br>
                    <div class="scrollable" id="othersCommentBoxDiv">`;

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
    <div><a href="#" class="text-decoration-none" disabled>${tempResponceData.patientName}</a></div>
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
      for (let i = 1; i < currentStar; i++) {
        tempCurrentStar = document.getElementById("star" + i + "_" + patientId + "_" + doctorId);
        tempCurrentStar.classList.remove("bi-star");
        tempCurrentStar.classList.add("bi-star-fill");
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
        addToast(false, "Success", "Doctors featched successfully!")
      }
    } catch (err) {
      addToast(true, "Error", "Some unknown error occurred. Unable to featch Doctors!" + err)
      return false;
    }
  })
    .fail(function (jqXHR, textStatus, errorThrown) {
      addToast(true, "Error", "Some unknown error occurred. Unable to featch Doctors!");
      return false;
    });;

  postButton.textContent = "Edit Comment";
  textCommentArea.setAttribute("disabled", "");
  //loadComents(doctorId);

}


function setStar(currentStar, patientId, doctorId) {
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


}