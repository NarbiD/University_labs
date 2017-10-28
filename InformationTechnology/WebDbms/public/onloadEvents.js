$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: "/databases/",
        success: [function (data) {
            for (var i=0; i<data.length; i++) {
                var row = $("<tr></tr>");
                $("<td>" + data[i] + "</td>").appendTo(row).on("click", function () {
                        clickOnDatabase(this.innerHTML);
                    });
                row.appendTo("#databaseList > tbody");
            }
        }]
    })
});

function clickOnDatabase(dbName) {
    $("#tableList").find("tbody > tr:gt(0)").remove();

    $.ajax({
        type: "GET",
        url: "/databases/" + dbName + "/tables/",
        success: [function (data) {
            for (var i=0; i<data.length; i++) {
                var row = $("<tr></tr>");
                $("<td>" + data[i] + "</td>").appendTo(row).on("click", function () {
                    clickOnTable(this.innerHTML);
                });
                row.appendTo("#tableList > tbody");
            }
        }]
    })
}

function clickOnTable(tableName) {

}
