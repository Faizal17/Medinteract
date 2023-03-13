var sendForm = document.querySelector('#chatform'),
    textInput = document.querySelector('.chatbox'),
    chatList = document.querySelector('.chatlist'),
    userBubble = document.querySelectorAll('.userInput'),
    botBubble = document.querySelectorAll('.bot__output'),
    animateBotBubble = document.querySelectorAll('.bot__input--animation'),
    overview = document.querySelector('.chatbot__overview'),
    hasCorrectInput,
    imgLoader = false,
    animationCounter = 1,
    animationBubbleDelay = 600,
    input,
    previousInput,
    isReaction = false,
    unkwnCommReaction = "I didn't quite get that.",
    chatbotButton = document.querySelector(".submit-button")


var viewDoctors = document.getElementById("viewDoctors");
viewDoctors.addEventListener("click", function(event){
  createBubble("alldoctors")
});

// var viewPatients = document.getElementById("viewPatients");
// viewPatients.addEventListener("click", function(event){
//   createBubble("viewPatients")
// });

var createAppointment = document.getElementById("createappointment");
createAppointment.addEventListener("click", function(event){
  createBubble("createAppointment")
});



var emergency = document.getElementById("emergencyInfo");
emergency.addEventListener("click", function(event){
  createBubble("emergency")
});



sendForm.onkeydown = function(e){
  if(e.keyCode == 13){
    e.preventDefault();

    //No mix ups with upper and lowercases
    var input = textInput.value.toLowerCase();

    //Empty textarea fix
    if(input.length > 0) {
      createBubble(input)
    }
  }
};

sendForm.addEventListener('submit', function(e) {
  //so form doesnt submit page (no page refresh)
  e.preventDefault();

  //No mix ups with upper and lowercases
  var input = textInput.value.toLowerCase();

  //Empty textarea fix
  if(input.length > 0) {
    createBubble(input)
  }
})

var createBubble = function(input) {
  //create input bubble
  var chatBubble = document.createElement('li');
  chatBubble.classList.add('userInput');

  //adds input of textarea to chatbubble list item
  chatBubble.innerHTML = input;

  //adds chatBubble to chatlist
  chatList.appendChild(chatBubble)

  checkInput(input);
}

var checkInput = function(input) {
  hasCorrectInput = false;
  isReaction = false;
const textVal = input.toString();

    //Is a word of the input also in possibleInput object?
    if(input == textVal || input.indexOf(textVal) >=0 && isReaction == false){
      console.log("has correct input");
      hasCorrectInput = true;
      botResponse(textVal);
    }
  }
  //When input is not in possibleInput
  if(hasCorrectInput == false){
    console.log("failed");
    unknownCommand(unkwnCommReaction);
    hasCorrectInput = true;

}

// debugger;

function botResponse(textVal) {
  //sets previous input to that what was called
  // previousInput = input;

  //create response bubble
  var userBubble = document.createElement('li');
  userBubble.classList.add('bot__output');

  // if(isReaction == true){
  //   if (typeof reactionInput[textVal] === "function") {
  //     //adds input of textarea to chatbubble list item
  //     userBubble.innerHTML = reactionInput[textVal]();
  //   } else {
  //     userBubble.innerHTML = reactionInput[textVal];
  //   }
  // }

  // if(isReaction == false){
    //Is the command a function?
    // if (typeof possibleInput[textVal] === "function") {
      // console.log(possibleInput[textVal] +" is a function");
      //adds input of textarea to chatbubble list item
      userBubble.innerHTML = possibleInput[textVal]();
    // } else {
    //   userBubble.innerHTML = possibleInput[textVal];
    // }
  // }
  //add list item to chatlist
  chatList.appendChild(userBubble) //adds chatBubble to chatlist

  // reset text area input
  textInput.value = "";
}

function unknownCommand(unkwnCommReaction) {
  // animationCounter = 1;

  //create response bubble
  var failedResponse = document.createElement('li');

  failedResponse.classList.add('bot__output');
  failedResponse.classList.add('bot__output--failed');

  //Add text to failedResponse
  failedResponse.innerHTML = unkwnCommReaction; //adds input of textarea to chatbubble list item

  //add list item to chatlist
  chatList.appendChild(failedResponse) //adds chatBubble to chatlist

  animateBotOutput();

  // reset text area input
  textInput.value = "";

  //Sets chatlist scroll to bottom
  chatList.scrollTop = chatList.scrollHeight;

  animationCounter = 1;
}



function emergencyResponse() {

  var response = document.createElement('li');
  response.classList.add('bot__output');



  // Get the info element
  // const infoElement = document.getElementById("info");

// Set the website URL
  const websiteUrl = "911.novascotia.ca";

// Set the information about calling 911
  const infoText = "Call 911 when someone’s health, safety or property is threatened and help is needed right away.";

// Display the information in the HTML document
  response.innerHTML = `<p><a href="${websiteUrl}">${websiteUrl}</a><p>${infoText}</p>`;

  //Adds whatever is given to responseText() to response bubble
  // response.innerHTML = e;

  chatList.appendChild(response);

  animateBotOutput();

  console.log(response.clientHeight);

  //Sets chatlist scroll to bottom
  setTimeout(function(){
    chatList.scrollTop = chatList.scrollHeight;
    console.log(response.clientHeight);
  }, 0)
}


function responseText(e) {

  var response = document.createElement('li');

  response.classList.add('bot__output');

  //Adds whatever is given to responseText() to response bubble
  response.innerHTML = e;

  chatList.appendChild(response);

  animateBotOutput();

  console.log(response.clientHeight);

  //Sets chatlist scroll to bottom
  setTimeout(function(){
    chatList.scrollTop = chatList.scrollHeight;
    console.log(response.clientHeight);
  }, 0)
}

function responseButtons(name,otherElement,id,customClassName) {


  console.log(id)
  console.log(name)
  var list = document.createElement('li');
  list.classList.add('bot__output');

  var button = document.createElement('button');
  button.classList.add('btn-primary', 'btn', customClassName)
  button.id= id+"_"+name
  button.innerHTML = name

  list.appendChild(button)
  if(otherElement!=null)
    list.appendChild(otherElement)
  chatList.appendChild(list);

  animateBotOutput();


  //Sets chatlist scroll to bottom
  setTimeout(function(){
    chatList.scrollTop = chatList.scrollHeight;
    console.log(list.clientHeight);
  }, 0)
}



//change to SCSS loop
function animateBotOutput() {
  chatList.lastElementChild.style.animationDelay= (animationCounter * animationBubbleDelay)+"ms";
  animationCounter++;
  chatList.lastElementChild.style.animationPlayState = "running";
}

function commandReset(e){
  animationCounter = 1;
  previousInput = Object.keys(possibleInput)[e];
}


var possibleInput = {

  "hi" : function(){
    responseText("Hello, How can i help you?");
    commandReset(2);
    return
  },
  "viewPatients":async function () {
    let url = "http://localhost:6969/patient/fetchAll";
    let responseData = await fetch(url)
    const data= await responseData.json();

    console.log(responseData);
    console.log(data.msg)
    for (let i = 0; i < data.data.length; i++) {
      console.log(data.data[i].patientName.toString());
      responseButtons(data.data[i].patientName.toString(), null, data.data[i].id,"calendar");
      commandReset(2);

    }
    return


  }
    ,
  "myBooking": function(){

      responseButtons("My booking",null,"myBookingId","myBooking");
  commandReset(2);
  return
}
  ,
  "emergency" : function (){
    emergencyResponse()
    commandReset(2)
    return
  },
//
//   "createAppointment" : async function () {
//
//
// //     const apiUrl = "http://localhost:6969/appointment/fetchDoctorNamesByAppointments";
// //
// // // Set the JSON data to send in the request body
// //     const jsonData = {
// //       "id": getCookie("id")
// //     };
// //     console.log(jsonData)
// //
// //     let responseData = await fetch(apiUrl, {
// //       method: "POST",
// //       headers: {
// //         "Content-Type": "application/json"
// //       },
// //       body: JSON.stringify(jsonData)
// //     })
// //     const data= await responseData.json();
// //     console.log(data)
//
//
//           // for (let i = 0; i < appointmentData.length; i++) {
//           //
//           //   // const data2 = await postData(doctorID);
//           //
//           //
//           //
//           //
//           //
//           //   // responseButtons(doctorData.doctorName, null, appointmentData[i].doctorId.toString(), "calendar");
//           //   // commandReset(2);
//           //
//           // }
//
//
//     return
//
//
//   },




  "alldoctors" : async function () {
    let url = "http://localhost:6969/doctor/fetchAll";
    let responseData = await fetch(url)
    const data= await responseData.json();

    // console.log(response);
    console.log(data.msg)
    for (let i = 0; i < data.data.length; i++) {
      console.log(data.data[i].doctorName.toString());
      responseButtons(data.data[i].doctorName.toString(), null, data.data[i].id,"calendar");
      commandReset(2);

    }
    return


  }




}


async function postData(doctorID) {
    let url = "http://localhost:6969/doctor/profile/"+doctorID;
    const response = await fetch(url);
      const data = await response.json();
      return data;

}


