function validateBooking() {
let persons = document.getElementById("persons").value;
if (persons <= 0) {
alert("Enter valid number of persons");
return false;
}
alert("Booking Submitted!");
return true;
}