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
var messagesRef2 = firebase.database().ref('StudentList');
var messagesRef3 = firebase.database().ref('Students');

// Listen for form submit students form

document.getElementById('stu_registerform').addEventListener('submit',submitform);

function submitform(e)
{
    e.preventDefault();

    var fname = getInputVal('first-name');
    var lname = getInputVal('last-name');
    var email = getInputVal('your_email');
    var mail = email.substring(0, email.lastIndexOf("@"));
    var contactno = getInputVal('phone');
    var regnno = getInputVal('regno');
    var course_1 = getInputVal('course1');
    var course_2 = getInputVal('course2');
    var course_3 = getInputVal('course3');
    var course_4 = getInputVal('course4');
    var d = getInputVal('date');
    var o = getInputVal('month');
    var b = getInputVal('year');

    saveMessage1( regnno, course_1, course_2, course_3, course_4);
    saveMessage2(mail, regnno);
    saveMessage3(fname, lname, email, contactno, regnno, course_1, course_2, course_3, course_4, d, o, b);
   

    document.querySelector('.alert').style.display = 'block';
    setTimeout(function () {
        document.querySelector('.alert').style.display = 'none';
    }, 3000);
    // Clear form
    document.getElementById('stu_registerform').reset();
}

//function to get form values
function getInputVal(id){
    return document.getElementById(id).value;
}

//Save messsage to firebase

function saveMessage1( regnno , course_1, course_2, course_3, course_4){
    console.log(course_1);
    var newMessageRef = messagesRef1.child(course_1).child("Students").child(regnno).child("Exams").child("MidSem");
    newMessageRef.set(0);
    var newMessageRef2 = messagesRef1.child(course_1).child("Students").child(regnno).child("Exams").child("Test1");
    newMessageRef2.set(0);
    var newMessageRef3 = messagesRef1.child(course_1).child("Students").child(regnno).child("Present");
    newMessageRef3.set(0);
    var newMessageRef13 = messagesRef1.child(course_1).child("Total");
    newMessageRef13.set(0);


    var newMessageRef4 = messagesRef1.child(course_2).child("Students").child(regnno).child("Exams").child("MidSem");
    newMessageRef4.set(0);
    var newMessageRef5 = messagesRef1.child(course_2).child("Students").child(regnno).child("Exams").child("Test1");
    newMessageRef5.set(0);
    var newMessageRef6 = messagesRef1.child(course_2).child("Students").child(regnno).child("Present");
    newMessageRef6.set(0);
    var newMessageRef14 = messagesRef1.child(course_2).child("Total");
    newMessageRef14.set(0);

    var newMessageRef7 = messagesRef1.child(course_3).child("Students").child(regnno).child("Exams").child("MidSem");
    newMessageRef7.set(0);
    var newMessageRef8 = messagesRef1.child(course_3).child("Students").child(regnno).child("Exams").child("Test1");
    newMessageRef8.set(0);
    var newMessageRef9 = messagesRef1.child(course_3).child("Students").child(regnno).child("Present");
    newMessageRef9.set(0);
    var newMessageRef15 = messagesRef1.child(course_3).child("Total");
    newMessageRef15.set(0);


    var newMessageRef10 = messagesRef1.child(course_4).child("Students").child(regnno).child("Exams").child("MidSem");
    newMessageRef10.set(0);
    var newMessageRef11 = messagesRef1.child(course_4).child("Students").child(regnno).child("Exams").child("Test1");
    newMessageRef11.set(0);
    var newMessageRef12 = messagesRef1.child(course_4).child("Students").child(regnno).child("Present");
    newMessageRef12.set(0);
    var newMessageRef16 = messagesRef1.child(course_4).child("Total");
    newMessageRef16.set(0);


}

function saveMessage2(mail, regnno) 
{
    var newMessageRef = messagesRef2;
    newMessageRef.child(mail).set(regnno);
}

function saveMessage3(fname, lname, email, contactno, regnno, course_1, course_2, course_3, course_4, d, o, b)
{
    var newMessageRef = messagesRef3.child(regnno).child("Courses").child(course_1);
    newMessageRef.set(3);
    var newMessageRef2 = messagesRef3.child(regnno).child("Courses").child(course_2);
    newMessageRef2.set(3);
    var newMessageRef3 = messagesRef3.child(regnno).child("Courses").child(course_3);
    newMessageRef3.set(3);
    var newMessageRef4 = messagesRef3.child(regnno).child("Courses").child(course_4);
    newMessageRef4.set(3);
    var newMessageRef5 = messagesRef3.child(regnno).child("FirstName");
    newMessageRef5.set(fname);
    var newMessageRef6 = messagesRef3.child(regnno).child("LastName");
    newMessageRef6.set(lname);
    var newMessageRef7 = messagesRef3.child(regnno).child("Email");
    newMessageRef7.set(email);
    var newMessageRef8 = messagesRef3.child(regnno).child("ContactNo");
    newMessageRef8.set(contactno);
    var newMessageRef9 = messagesRef3.child(regnno).child("DOB").child("Date");
    newMessageRef9.set(d);
    var newMessageRef10 = messagesRef3.child(regnno).child("DOB").child("Month");
    newMessageRef10.set(o);
    var newMessageRef11 = messagesRef3.child(regnno).child("DOB").child("Year");
    newMessageRef11.set(b);
}
