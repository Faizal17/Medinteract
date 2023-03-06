//const globalURL = "http://localhost:6969/";

function getCityName(tempResponceData, doctorList) {
  console.log(doctorList)
  console.log(tempResponceData);

  let responseData;

  $.ajax({
    url: globalURL + 'city/city_name_with_province',
    type: "POST",
    dataType: "json",
    contentType: "application/json",
    data: JSON.stringify(tempResponceData),

  }).done(function (response) {
    console.log(response);

    tempResponceData.doctorAddressProvince = response.data[0];
    tempResponceData.doctorAddressCity = response.data[1];

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

        console.log("here in get city");

        //addToast(false, "Success", "Doctors featched successfully!")
      }
    } catch (err) {
      addToast(true, "Error", "Some unknown error occurred. Unable to featch City Name!" + err)
      return false;
    }
  })
    .fail(function (jqXHR, textStatus, errorThrown) {
      addToast(true, "Error", "Some unknown error occurred. Unable to featch City Name!");
      return false;
    });;

}



function searchdoctor(event) {

  const doctorList = document.getElementById("doctor_list_div");

  var searchType = document.getElementsByName('doctor_search_options');
  var selectedType = '';
  for (var i = 0; i < searchType.length; i++) {
    if (searchType[i].checked) {
      selectedType = searchType[i].value;
      break;
    }
  }

  var searchbar = document.getElementById('search_input');
  var searchbarInput = searchbar.value;

  let data;
  let apiUrl;
  console.log(selectedType);

  if (selectedType == "city") {
    console.log("In city");
    searchbarInput = parseInt(searchbarInput, 10);
    apiUrl = "doctor/city";
    data = {
      "doctorAddressCity": searchbarInput
    };

    console.log(data);
  }

  else if (selectedType == "province") {
    console.log("In province");
    searchbarInput = parseInt(searchbarInput, 10);
    apiUrl = "doctor/province";
    data = {
      "doctorAddressProvince": searchbarInput
    };
  }

  else if (selectedType == "name") {
    console.log("In name");
    apiUrl = "doctor/name";
    data = {
      "doctorName": searchbarInput
    };
  }

  else if (selectedType == "qualification") {
    console.log("In qualification");
    apiUrl = "doctor/qualification";
    data = {
      "doctorQualification": searchbarInput
    };
  }

  let responseData;

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

          console.log(tempResponceData);
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
      responseData = response.data;

      // console.log(responseData);
      if (response.isError) {
        //responseData = response;
        addToast(true, "Error", response.msg);
        return false;
      } else {
        doctorList.innerHTML = "";
        for (let i = 0; i < responseData.length; i++) {
          let tempResponceData = responseData[i];
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

});