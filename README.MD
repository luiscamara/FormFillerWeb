<h1>PDF Form Filler Web Service</h1>
<h4>Project developed for VanHackathon in November/2019</h4>
<p>
This application is a REST Web Service that receives a PDF Form document 
and fills its fields according to input data. The intent is to
use this web service to automatically fill Canada's Global Talent Stream Application,
but it can be used to fill in any PDF Form.
<p>
This project was developed using:
Java, Spring Boot, MongoDB and PDFBox.
</p>
<h3>Instructions</h3>
<p>1) Publish this service on a Java Application Server, such as Tomcat.</p>
<p>2) Access the web service.</p>
<ul>
    <li>Upload the PDF Form using "http://yourserver.host.com/add". Include a Multipart form file with the name "pdf".</li>
    <li>Get the document ID from the "add" operation.</li>
    <li>Access all the fields available on the PDF Form through "http://yourserver.host.com/DOCUMENT_ID/listFields"</li>
    <li>Send a JSON object to "http://yourserver.host.com/DOCUMENT_ID/fill".
        <ul>
        <li>The JSON should contain a key and a value for each input field.</li>
        <li>The key is the fully qualified name of the target input field.</li>
        <li>The value depends on the type of the field. More information on operation "listFields".</li>
        </ul>
    </li>
    <li>Download the filled PDF document</li>
</ul>

