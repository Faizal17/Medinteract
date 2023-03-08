//const globalURL = "http://localhost:6969/";

function getCityName(tempResponceData, doctorList) {
  //console.log(doctorList)
  //console.log("........."+tempResponceData.doctorAddressCity+JSON.stringify(tempResponceData));

  let responseData;
  let cidtyData = {
    "id": tempResponceData.doctorAddressCity
  }

  $.ajax({
    url: globalURL + 'city/city_name_with_province',
    type: "POST",
    dataType: "json",
    contentType: "application/json",
    data: JSON.stringify(cidtyData),

  }).done(function (response) {
    //console.log(response.data[0][0]);

    tempResponceData.doctorAddressProvince = response.data[0][0];
    tempResponceData.doctorAddressCity = response.data[0][1];

    let htmlString = `<div class="card col-md-6 mx-auto"  id="doctor_list_div_card" style="width: 35rem;">
          <div class="card-body">
            <h5 class="card-title">`+ tempResponceData.doctorName + `</h5>
            <p class="card-text">Dr. `+ tempResponceData.doctorName + ` is a ` + tempResponceData.doctorType + ` who provieds their services in ` + tempResponceData.doctorAddressCity + `</p>
            <ul class="list-group list-group-flush">
              <li class="list-group-item">Email: `+ tempResponceData.doctorEmail + `</li>
              <li class="list-group-item">From: `+ tempResponceData.doctorAddressProvince + `,` + tempResponceData.doctorAddressCity + `</li>
              <li class="list-group-item">Qualifications: `+ tempResponceData.doctorQualification + `</li>
            </ul>
            
            <button id="${tempResponceData.id}_${tempResponceData.doctorName}" class="btn btn-primary float-end calendar" style="width: 10rem;">Book a Appointment</button>
            
          </div>
        </div>`;

    let div = document.createElement("div");
    div.id = "doctor_list_div_subdiv";
    div.innerHTML = htmlString;

    doctorList.appendChild(div);
    doctorList.appendChild(document.createElement("br"))

    try {
      responseData = response;

      if (responseData.isError) {
        addToast(true, "Error", responseData.msg);
        return false;
      } else {

        //console.log("here in get city");

        //addToast(false, "Success", "Doctors fetched successfully!")
      }
    } catch (err) {
      addToast(true, "Error", "Some unknown error occurred. Unable to fetch City Name!" + err)
      return false;
    }
  })
    .fail(function (jqXHR, textStatus, errorThrown) {
      addToast(true, "Error", "Some unknown error occurred. Unable to fetch City Name!");
      return false;
    });;

}

function searchdoctor(event) {


  //console.log("In searchDoctor ");
  //event.preventDefault();
  let apiUrl;
  let data;
  //const id = this.id;



  apiUrl = "doctor/get_doctor_on_doctor_details";
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
          getCityName(tempResponceData, doctorList);

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

  $.ajax({
    url: globalURL + "doctor/fetchAll",
    type: "GET",
    dataType: "json",
    contentType: "application/json",

  }).done(function (response) {
    //console.log(response);

    try {
      responseData = response;

      if (responseData.isError) {
        //responseData = response;
        addToast(true, "Error", responseData.msg);
        return false;
      } else {
        doctorList.innerHTML = "";
        for (let i = 0; i < responseData.length; i++) {
          let tempResponceData = responseData[i];
          getCityName(tempResponceData, doctorList);
        }
        addToast(false, "Success", "Doctors fetched successfully!")
      }
    } catch (err) {
      addToast(true, "Error", "Some unknown error occurred. Unable to fetch Doctors!" + err)
      return false;
    }
  })
    .fail(function (jqXHR, textStatus, errorThrown) {
      addToast(true, "Error", "Some unknown error occurred. Unable to fetch Doctors!");
      return false;
    });;

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