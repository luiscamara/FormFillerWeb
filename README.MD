<h1>PDF Form Filler Web Service</h1>
<h4>Project developed for VanHackathon in November/2019</h4>
<p>
This application is a REST Web Service that receives a PDF Form document 
and fills its fields according to input data. The intent is to
use this web service to automatically fill Canada's Global Talent Stream Application,
but it can be used to fill in any PDF Form.

<p>
This project was developed using:<br />
Language: Java<br />
Web Framework: Spring Boot<br />
NoSQL Database: MongoDB<br />
External libraries: PDFBox<br />
</p>

<hr />

<h3>REST API</h3>
<h4>Method: Add a PDF Form</h4>
<h5>Endpoint: /add</h5>
<h5>Request:</h5>
<p>Send a POST Request with Multipart form with "pdf" as key and the PDF Form file as the value.</p>
<h5>Response description: </h5>
<p>The ID of the newly uploaded document</p>
<h5>Description:</h5>
<p>Use this method to insert a new PDF Form that you wish to fill using the service. Example of request and response:</p>
![Image of Add Request](http://github.com/luiscamara/FormFillerWeb/tutorials_assets/add-request.png)
<hr />
<h4>Method: List documents</h4>
<h5>Endpoint: /list</h5>
<h5>Request:</h5>
<p>Simple GET request to endpoint.</p>
<h5>Response description: </h5>
<p>JSON array containing JSON objects with document ID, file name and creation date.</p>
<h5>Description:</h5>
<p>Use this method to list all PDF Forms that were uploaded to the service. 
Use the document ID to execute other methods, like filling a form. Example of request and response:</p>
![Image of List Request](http://github.com/luiscamara/FormFillerWeb/tutorials_assets/list-request.png)
<hr />
<h4>Method: Remove document</h4>
<h5>Endpoint: /<&lt;DOCUMENT_ID&gt;>/remove</h5>
<h5>Request:</h5>
<p>Simple GET request to endpoint. Include document ID on the endpoint URL.</p>
<h5>Response description: </h5>
<p>Empty</p>
<h5>Description:</h5>
<p>Use this method to remove one PDF Form that were uploaded to the service. 
Example of request and response:</p>
![Image of Remove Request](http://github.com/luiscamara/FormFillerWeb/tutorials_assets/remove-request.png)
<hr />
<h4>Method: List document input fields</h4>
<h5>Endpoint: /<&lt;DOCUMENT_ID&gt;>/listfields</h5>
<h5>Request:</h5>
<p>Simple GET request to endpoint. Include document ID on the endpoint URL.</p>
<h5>Response description: </h5>
<p>JSON Array with JSON objects representing each input field. The available information is:</p>
<ul>
<li>fullyQualifiedName - This field is required for form filling.</li>
<li>name - Registered name of the form input field.</li>
<li>type - Type of the form input field.</li>
<li>description - Text describing the input field.</li>
<li>page - In which page the input field is located.</li>
<li>possibleInputs - Possible values to set to this input field.</li>
</ul>
<h5>Description:</h5>
<p>Use this method to collect vital information about the input fields available in the PDF Form. 
The fullyQualifiedName and possibleInputs will be important to fill in the fields in the Fill Method.
Example of request and response:</p>
![Image of List Fields Request](http://github.com/luiscamara/FormFillerWeb/tutorials_assets/listfields-request.png)
<hr />
<h4>Method: Fill PDF form</h4>
<h5>Endpoint: /<&lt;DOCUMENT_ID&gt;>/fill</h5>
<h5>Request:</h5>
<p>
POST Request containing a JSON object on its body. 
The JSON object should have a list of input fields that the user wants to fill in.
The fullyQualifiedName should be used as the key and one of the possible values as the value. 
Include document ID on the endpoint URL.
</p>
<h5>Response description: </h5>
<p>The response body will contain the filled PDF Form.</p>
<h5>Description:</h5>
<p>Use this method to fill in input fields in a PDF Form.
Example of request and response:</p>
![Image of Fill Request](http://github.com/luiscamara/FormFillerWeb/tutorials_assets/fill-request.png)
<hr />
<h3>Sample workflow</h3>
<p>1) Upload your PDF Form to the service.</p>
<p>2) List available fields.</p>
<p>3) Build JSON request according to the input fields available.</p>
<p>4) Send JSON object to the Fill Request and download the filled PDF form.</p>
<hr />
<h3>Demonstration Service Available</h3>
<h5>URL:</h5>
<p>https://pdfformfiller.herokuapp.com/</p>
<h5>Sample files:</h5>
<p><a href="http://github.com/luiscamara/FormFillerWeb/tutorials_assets/original.pdf">Sample PDF file - Original</a></p>
<p><a href="http://github.com/luiscamara/FormFillerWeb/tutorials_assets/filled.pdf">Sample PDF file - Filled</a></p>
<p><a href="http://github.com/luiscamara/FormFillerWeb/tutorials_assets/sample.json">JSON used to fill Sample PDF file</a></p>