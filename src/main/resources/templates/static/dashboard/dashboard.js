//const globalURL = "http://localhost:6969/";

function searchdoctor(event) {
  /*
  var form = document.getElementById("doctor_search_form");
  function handleForm(event) { event.preventDefault(); }
  form.addEventListener('submit', handleForm);*/

  console.log("In search..........");

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
          let htmlString = `<div class="card col-md-6 mx-auto"  id="doctor_list_div_card" style="width: 35rem;">
          <div class="card-body">
            <h5 class="card-title">`+ tempResponceData.doctorName + `</h5>
            <p class="card-text">Dr. `+ tempResponceData.doctorName + ` is a ` + tempResponceData.doctorType + ` who provieds their services in ` + tempResponceData.doctorAddressCity + `</p>
            <ul class="list-group list-group-flush">
              <li class="list-group-item">Email: `+ tempResponceData.doctorEmail + `</li>
              <li class="list-group-item">From: `+ tempResponceData.doctorAddressProvince + `,` + tempResponceData.doctorAddressCity + `</li>
              <li class="list-group-item">Qualifications: `+ tempResponceData.doctorQualification + `</li>
            </ul>
            
            <a href="#" class="btn btn-primary float-end" style="width: 10rem;">Book a Appointment</a>
            
          </div>
        </div>`;

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