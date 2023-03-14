//const globalURL = "http://localhost:6969/";

function showDoctorList(tempResponceData, doctorList) {
  //console.log(doctorList)
  //console.log("........."+tempResponceData.doctorAddressCity+JSON.stringify(tempResponceData));



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
          
            <div class="rating">
            <div class="rating-stars">
            <span><i class="bi bi-star" id="start1_${tempResponceData.id}"></i></span>
            <span><i class="bi bi-star" id="star2_${tempResponceData.id}"></i></span>
            <span><i class="bi bi-star" id="star3_${tempResponceData.id}"></i></span>
            <span><i class="bi bi-star" id="star4_${tempResponceData.id}"></i></span>
            <span><i class="bi bi-star" id="star5_${tempResponceData.id}"></i></span>
            </div>
            </div>
            <button id="${tempResponceData.id}_${tempResponceData.doctorName}" class="btn btn-primary float-end calendar" style="width: 10rem;">Book a Appointment</button>
            
          </div>
        </div>`;

  let div = document.createElement("div");
  div.id = "doctor_list_div_subdiv";
  div.innerHTML = htmlString;

  let ratingStar1 = document.getElementById("start1_" + tempResponceData.id);
  let ratingStar2 = document.getElementById("start2_" + tempResponceData.id);
  let ratingStar3 = document.getElementById("start3_" + tempResponceData.id);
  let ratingStar4 = document.getElementById("start4_" + tempResponceData.id);
  let ratingStar5 = document.getElementById("start5_" + tempResponceData.id);



  doctorList.appendChild(div);
  doctorList.appendChild(document.createElement("br"))




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
    contentType: "application/json",
    data: JSON.stringify(data),

  }).done(function (response) {

    try {
      responseData = response;

      if (responseData.isError) {
        addToast(true, "Error", responseData.msg);
        return false;
      } else {

        doctorList.innerHTML = "";

        for (let i = 0; i < responseData.data.length; i++) {

          let tempResponceData = responseData.data[i];

          //console.log(tempResponceData);
          showDoctorList(tempResponceData, doctorList);

        }
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