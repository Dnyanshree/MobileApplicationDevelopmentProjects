// Initialize your app
var myApp = new Framework7();

// Export selectors engine
var $$ = Dom7;

// Add view
var mainView = myApp.addView('.view-main', {
    // Because we use fixed-through navbar we can enable dynamic navbar
    dynamicNavbar: true
});

// Callbacks to run specific code for specific pages, for example for About page:
myApp.onPageInit('about', function (page) {
    // run createContentPage func after link was clicked
    $$('.create-page').on('click', function () {
        createContentPage();
    });
});

// Generate dynamic page
var dynamicPageIndex = 0;
function createContentPage() {
	mainView.router.loadContent(
        '<!-- Top Navbar-->' +
        '<div class="navbar">' +
        '  <div class="navbar-inner">' +
        '    <div class="left"><a href="#" class="back link"><i class="icon icon-back"></i><span>Back</span></a></div>' +
        '    <div class="center sliding">Dynamic Page ' + (++dynamicPageIndex) + '</div>' +
        '  </div>' +
        '</div>' +
        '<div class="pages">' +
        '  <!-- Page, data-page contains page name-->' +
        '  <div data-page="dynamic-pages" class="page">' +
        '    <!-- Scrollable page content-->' +
        '    <div class="page-content">' +
        '      <div class="content-block">' +
        '        <div class="content-block-inner">' +
        '          <p>Here is a dynamic page created on ' + new Date() + ' !</p>' +
        '          <p>Go <a href="#" class="back">back</a> or go to <a href="services.html">Services</a>.</p>' +
        '        </div>' +
        '      </div>' +
        '    </div>' +
        '  </div>' +
        '</div>'
    );
	return;
}


function capturePhoto(){
           
            navigator.camera.getPicture(onPhotoDataSuccess, onFail, { 
                destinationType: Camera.DestinationType.FILE_URI,
                sourceType: Camera.PictureSourceType.PHOTOLIBRARY   });
        }

        
        function onPhotoDataSuccess (imageData) {
           

            var image = document.getElementById('gallerySrc');
            image.value = imageData;
            alert('Success');
            
        }

        function onFail(message){
            alert('Failed because: ' + message);
        }

function onSuccess(contact) {
    alert("Save Success");
};

function onError(contactError) {
    alert("Error = " + contactError.code);
};

function saveContact(){

    var contact = navigator.contacts.create();

    // store contact phone numbers in ContactField[]
    var phoneNumbers = [];
    phoneNumbers[0] = new ContactField('work', document.getElementById('work').value, false);
    phoneNumbers[1] = new ContactField('mobile', document.getElementById('mobile').value, true); // preferred number
    phoneNumbers[2] = new ContactField('home', document.getElementById('home').value, false);
    contact.phoneNumbers = phoneNumbers;

    var name = new ContactName();
    name.givenName = document.getElementById('name').value;
    contact.name = name;
    //email
    var emails = [];
    emails[0] = new ContactField('email', document.getElementById('email').value, true);   
    contact.emails = emails;

    //url
    var urls = [];
    urls[0] = new ContactField('url', document.getElementById('url').value, true);   
    contact.urls = urls;
    
    //bday
    contact.birthday = new Date(document.getElementById('bday').value);

    //picture
    var photos = [];
    photos[0] = new ContactField('pic', document.getElementById('gallerySrc').value, true);

    if(document.getElementById("gallerySrc").value.toString().length > 0){                    
                    contact.photos = photo;
                }   
   // contact.photos = photos;
    // save the contact
    contact.save(onSuccess,onError);
}