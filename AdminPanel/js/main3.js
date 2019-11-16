$(function () {
    $("#form-total").steps({
        headerTag: "h2",
        bodyTag: "section",
        transitionEffect: "fade",
        enableAllSteps: true,
        stepsOrientation: "vertical",
        autoFocus: true,
        transitionEffectSpeed: 500,
        titleTemplate: '<div class="title">#title#</div>',
        labels: {
            previous: 'Back Step',
            next: '<i class="zmdi zmdi-arrow-right"></i>',
            finish: '<i class="zmdi zmdi-check"></i>',
            current: ''
        },
    })
});

// Your web app's Firebase configuration
var firebaseConfig = {
    apiKey: "AIzaSyAn_7uezdNS4WFUTzlaFIEp6llEClRF9pw",
    authDomain: "hackatron-49301.firebaseapp.com",
    databaseURL: "https://hackatron-49301.firebaseio.com",
    projectId: "hackatron-49301",
    storageBucket: "hackatron-49301.appspot.com",
    messagingSenderId: "906193267360",
    appId: "1:906193267360:web:4ce4a3e5ec6905ef0b77c5"
};
// Initialize Firebase
firebase.initializeApp(firebaseConfig);

//Reference messages collection 

var messagesRef1 = firebase.database().ref('Courses');

// Listen for form submit students form

document.getElementById('course-register').addEventListener('submit', submitform);

function submitform(e) {
    e.preventDefault();

    var coursename = getInputVal('course-name');
    var courseId = getInputVal('courseid');
    var description = getInputVal('message');

    saveMessage1(coursename,courseId,description);

    document.querySelector('.alert').style.display = 'block';
    setTimeout(function () {
        document.querySelector('.alert').style.display = 'none';
    }, 3000);
    // Clear form
    document.getElementById('course-register').reset();
}

//function to get form values
function getInputVal(id) {
    return document.getElementById(id).value;
}

//Save messsage to firebase

function saveMessage1(coursename, courseId, description) {
    var newMessageRef2 = messagesRef1.child(courseId).child("CourseName");
    newMessageRef2.set(coursename);
    var newMessageRef3 = messagesRef1.child(courseId).child("CourseDescription");
    newMessageRef3.set(description);
    var newMessageRef4 = messagesRef1.child(courseId).child("Total");
    newMessageRef4.set(0);
}
