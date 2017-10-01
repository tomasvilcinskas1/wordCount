
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js"></script>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Untitled</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
</head>

<body>
<form method="POST" enctype="multipart/form-data" id="fileUploadForm">
    <input type="file" name="files" accept=".txt"  multiple/><br/><br/>
    <input type="submit" value="Submit" id="btnSubmit"/>
</form>
<div id="proccess_div">
<h1>Ajax Post Result</h1>
<pre>
    <span id="result"></span>
</pre>
    <h1 id="proccess_h" >Process the uploaded files</h1>
    <button id="processBtn">Process</button>
    <h1>Processed results</h1>
    <div id="processResults">
        <div class="table-responsive">
            <table class="table">
                <thead>
                    <tr>
                        <th>Word</th>
                        <th>Count</th>
                    </tr>
                </thead>
            </table>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
        $("#proccess_div").hide();
        $("#processBtn").click(function (event) {
            $.get("/fileProcess",function (data,status) {
                $("#processResults").html(data);

            });
        });
        $("#btnSubmit").click(function (event) {

            //stop submit the form.
            event.preventDefault();

            fire_ajax_submit();

        });

    });

    function fire_ajax_submit() {

        // Get form
        var form = $('#fileUploadForm')[0];

        var data = new FormData(form);

        $("#btnSubmit").prop("disabled", true);

        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "/",
            data: data,
            processData: false, //prevent jQuery from automatically transforming the data into a string
            contentType: false,
            cache: false,
            success: function (data) {

                $("#result").text(data);
                console.log("SUCCESS : ", data);
                $("#btnSubmit").prop("disabled", false);
                $("#proccess_div").show()

            },
            error: function (e) {

                $("#result").text(e.responseText);
                console.log("ERROR : ", e);
                $("#btnSubmit").prop("disabled", false);

            }
        });

    }

</script>
</body>

</html>