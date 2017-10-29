$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: "/databases/",
        success: [function (data) {
            clickOnListItem(data, function () {
                clickOnDatabase(this.innerHTML);
            });
        }]
    })
});

function clickOnListItem(list, action) {
    for (var i=0; i<list.length; i++) {
        var row = $("<tr></tr>");
        $("<td>" + list[i] + "</td>").appendTo(row).on("click", action);
        row.appendTo("#tableList > tbody");
    }
}

function clickOnDatabase(dbName) {
    cleanTable("#tableList");
    cleanTable("#entries");
    cleanImage();

    $.ajax({
        type: "GET",
        url: "/databases/" + dbName + "/tables/",
        success: [function (data) {
            clickOnListItem(data, function () {
                clickOnTable(dbName, this.innerHTML);
            });
        }]
    })
}

function clickOnTable(dbName, tableName) {
    cleanTable("#entries");
    cleanImage();
    $.ajax({
        type: "GET",
        url: "/databases/" + dbName + "/tables/" + tableName + "/rows/",
        success: [function (data) {
            for (var i=0; i < data.length; i++) {
                var values = data[i].values;
                var row = $("<tr></tr>");
                for (var j=0; j < values.length; j++){
                    $("<td>" + values[j] + "</td>").appendTo(row).on("click", function () {
                        var k = $(this).parent('tr').index();
                        $("#image").attr("src", "data:image/png;base64," + data[k-1].image);
                    });
                }
                row.appendTo("#entries > tbody");
            }
        }]
    })
}

function cleanTable(selector) {
    $(selector).find("tbody > tr:gt(0)").remove();
}

function cleanImage() {
    $("#image").removeAttr("src");
}
