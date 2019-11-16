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

var messagesRef1 = firebase.database().ref('TeacherList');
var messagesRef2 = firebase.database().ref('Teachers');
var messagesRef3 = firebase.database().ref('TeacherData');

// Listen for form submit students form

document.getElementById('tcr_registerform').addEventListener('submit', submitform);

function submitform(e) {
    e.preventDefault();

    var fname = getInputVal('first-name');
    var lname = getInputVal('last-name');
    var email = getInputVal('your_email');
    var mail = email.substring(0, email.lastIndexOf("@"));
    var contactno = getInputVal('phone');
    var teachersid = getInputVal('teacherid');
    var course_1 = getInputVal('course1');
    var course_2 = getInputVal('course2');
    var course_3 = getInputVal('course3');
    var course_4 = getInputVal('course4');
    var d = getInputVal('date');
    var o = getInputVal('month');
    var b = getInputVal('year');

    saveMessage1(mail, teachersid);
    saveMessage2(teachersid, course_1, course_2, course_3, course_4);
    
    saveMessage3(fname, lname, email, contactno, course_1, course_2, course_3, course_4, teachersid, d, o, b);


    document.querySelector('.alert').style.display = 'block';
    setTimeout(function () {
        document.querySelector('.alert').style.display = 'none';
    }, 3000);
    // Clear form
    document.getElementById('tcr_registerform').reset();
}

//function to get form values
function getInputVal(id) {
    return document.getElementById(id).value;
}

//Save messsage to firebase

function saveMessage1(mail, teachersid) {
    var newMessageRef = messagesRef1;
    newMessageRef.child(mail).set(teachersid);
}


function saveMessage2(teachersid, course_1, course_2, course_3, course_4) {
    var newMessageRef = messagesRef2.child(teachersid).child(course_1);
    newMessageRef.set(3);
    var newMessageRef = messagesRef2.child(teachersid).child(course_2);
    newMessageRef.set(3);
    var newMessageRef = messagesRef2.child(teachersid).child(course_3);
    newMessageRef.set(3);
    var newMessageRef = messagesRef2.child(teachersid).child(course_4);
    newMessageRef.set(3);
}


function saveMessage3(fname, lname, email, contactno, course_1, course_2, course_3, course_4 , teachersid, d, o, b) {
    var newMessageRef = messagesRef3.child(teachersid).child("Courses").child("Course1");
    newMessageRef.set(course_1);
    var newMessageRef2 = messagesRef3.child(teachersid).child("Courses").child("Course2");
    newMessageRef2.set(course_2);
    var newMessageRef3 = messagesRef3.child(teachersid).child("Courses").child("Course3");
    newMessageRef3.set(course_3);
    var newMessageRef4 = messagesRef3.child(teachersid).child("Courses").child("Course4");
    newMessageRef4.set(course_4);
    var newMessageRef5 = messagesRef3.child(teachersid).child("FirstName");
    newMessageRef5.set(fname);
    var newMessageRef6 = messagesRef3.child(teachersid).child("LastName");
    newMessageRef6.set(lname);
    var newMessageRef7 = messagesRef3.child(teachersid).child("Email");
    newMessageRef7.set(email);
    var newMessageRef8 = messagesRef3.child(teachersid).child("ContactNo");
    newMessageRef8.set(contactno);
    var newMessageRef9 = messagesRef3.child(teachersid).child("DOB").child("Date");
    newMessageRef9.set(d);
    var newMessageRef10 = messagesRef3.child(teachersid).child("DOB").child("Month");
    newMessageRef10.set(o);
    var newMessageRef11 = messagesRef3.child(teachersid).child("DOB").child("Year");
    newMessageRef11.set(b);
}
