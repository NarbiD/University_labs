$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: "/databases/",
        success: [function (data) {
            clickOnListItem(data, ".databaseList", function () {
                clickOnDatabase(this.innerHTML);
            });
        }]
    })
});

function clickOnListItem(list, selector, action) {
    for (var i = 0; i < list.length; i++) {
        var row = $("<tr></tr>");
        $("<td>" + list[i] + "</td>").appendTo(row).on("click", action);
        row.appendTo(selector + " > tbody");
    }
}

function clickOnDatabase(dbName) {
    cleanTable(".tableList");
    cleanTable(".entriesList");
    cleanImage();

    $.ajax({
        type: "GET",
        url: "/databases/" + dbName + "/tables/",
        success: [function (data) {
            clickOnListItem(data, ".tableList", function () {
                clickOnTable(dbName, this.innerHTML);
            });
        }]
    })
}

function clickOnTable(dbName, tableName) {
    cleanTable(".entriesList");
    cleanImage();

    $.ajax({
        type: "GET",
        url: "/databases/" + dbName + "/tables/" + tableName + "/rows/",
        success: [function (data) {
            $(".entriesList").find("th").attr("colspan", data[0].values.length) ;

            for (var i = 0; i < data.length; i++) {
                var values = data[i].values;
                var row = $("<tr></tr>");
                for (var j = 0; j < values.length; j++){
                    $("<td>" + values[j] + "</td>").appendTo(row).on("click", function () {
                        var k = $(this).parent('tr').index();
                        $(".image").attr("src", "data:image/png;base64," + data[k-1].image);
                    });
                }
                row.appendTo(".entriesList > tbody");
            }
        }]
    })
}

function cleanTable(selector) {
    $(selector).find("tbody > tr:gt(0)").remove();
}

function cleanImage() {
    $(".image").removeAttr("src");
}
