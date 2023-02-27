//const globalURL = "http://localhost:6969/";

function searchdoctor() {

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

  let responseData;

  $.ajax({
    url: globalURL + apiUrl,
    type: "POST",
    dataType: "json",
    contentType: "application/json",
    data: JSON.stringify(data),

  }).done(function (response) {
    //console.log(response);

    try {
      responseData = response;

      if (responseData.isError) {
        //responseData = response;
        addToast(true, "Error", responseData.msg);
        return false;
      } else {

        console.log(responseData);
        //console.log(responseData.data[0]);

        doctorList.innerHTML = "";



        for (let i = 0; i < responseData.data.length; i++) {
          console.log(responseData.data[i])

          console.log("Here..1");
          let tempResponceData = responseData.data[i];
          let htmlString = "Name: " + tempResponceData.doctorName + "Email: " + tempResponceData.doctorEmail + "Province: " + tempResponceData.doctorAddressProvince + "City: " + tempResponceData.doctorAddressCity + "Qualifications: " + tempResponceData.doctorQualification + "";
          let div = document.createElement("div");
          div.id = "doctor_list_div_subdiv";
          div.innerHTML = htmlString;

          doctorList.appendChild(div);
          doctorList.appendChild(document.createElement("br"))

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

//function 