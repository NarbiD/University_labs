var dataTypes = [];

$(document).ready(function () {

    $.ajax({
        type: "GET",
        url: "/types/",
        success: [function (data) {
            dataTypes = data;
            initTableForm(dataTypes, 2);
        }]
    });

    hideForm(".createDatabaseFormSection");
    hideForm(".createTableFormSection");
    loadDatabases();

    $(".createDatabaseForm").on("submit", function () {
        var outputJson = Object();
        outputJson.databaseName = $(".databaseName").val();
        outputJson.database = [1,2,3,4,5];
        $.ajax({
            type: "POST",
            url: "/databases/",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(outputJson),
            processData: false,
            success: function () {
                hideForm(".createDatabaseFormSection");
                cleanTable(".databaseList");
                loadDatabases();
            },
            error: function (data) {
                alert(JSON.parse(data.responseText).message.split(":")[1]);
            }
        })
    });

    $(".createTableForm").on("submit", function () {
        var outputJson = parseDataFromTableForm();
        var dbName = $(".databaseList").find("tr.selected").find("td").text();
        $.ajax({
            type: "POST",
            url: "/databases/" + dbName + "/tables/",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(outputJson),
            processData: false,
            success: function () {
                hideForm(".createTableFormSection");
                cleanTable(".tableList");
                loadTables(dbName);
            },
            error: function (data) {
                alert(JSON.parse(data.responseText).message.split(":")[1]);
            }
        })
    });

    function parseDataFromTableForm() {
        var outputJson = Object();
        outputJson.tableName = $(".tableName").val();
        var columnNames = $(".createTableForm .columnName").filter(function() {
            return $(this).val() !== "";
        });
        outputJson.columnNames = new Array(columnNames.length);
        for (var i = 0; i < columnNames.length; i++) {
            outputJson.columnNames[i] = columnNames.eq(i).val() + "";
        }
        outputJson.columnTypes = new Array(outputJson.columnNames.length);
        var columnTypes = $(".createTableForm .columnType");
        for (var j = 0; j < columnTypes.length; j++) {
            if (columnNames.eq(j).val() !== undefined) {
                outputJson.columnTypes[j] = dataTypes[columnTypes.eq(j).val()];
            }
        }
        var constraint = {};
        constraint.minValue = parseInt($(".constraintMin").val());
        constraint.maxValue = parseInt($(".constraintMax").val());
        outputJson.realIntervalConstraint = constraint;
        return outputJson;
    }

    $(".cancelButton").on("click", function () {
        hideForm("." + this.parentNode.parentNode.className);
    });

    $("#btnCreateDatabase").on("click", function () {
        showForm(".createDatabaseFormSection");
    });

    $("#btnDeleteDatabase").on("click", function () {
        deleteDatabase();
    });

    $("#btnDeleteTable").on("click", function () {
        deleteTable();
    });

    $("#btnCreateTable").on("click", function () {

        $(".addColumn").on("click", function () {
            addColumnToTableForm(dataTypes);
        });

        if($(".databaseList").find("tr.selected").length == 1) {
            showForm(".createTableFormSection");
        }
    });

    $("#btnCreateRow").on("click", function () {
        // showForm(".create");
    });

    $("#btnDeleteRow").on("click", function () {
    });
});



function addColumnToTableForm(dataTypes) {
    var columnTypeField = $("<select title=\"type\" class=\"columnType\"></select><br>");
    for(var typeNumber = 0; typeNumber < dataTypes.length; typeNumber++) {
        $("<option value=" + typeNumber + ">" + dataTypes[typeNumber] + "</option>").appendTo(columnTypeField);
    }
    var columnNameField = $("<input type=\"text\" class=\"columnName\" title=\"column name\">");
    var columnProps = $("<div></div>");
    columnProps.append(columnNameField);
    columnProps.append(columnTypeField);
    $(".addColumn").before(columnProps);
}

function initTableForm(dataTypes, columnAmount) {
    for (var i = 0; i < columnAmount; i++) {
        addColumnToTableForm(dataTypes);
    }
}

function loadDatabases() {
    $.ajax({
        type: "GET",
        url: "/databases/",
        success: [function (data) {
            clickOnListItem(data, ".databaseList", function () {
                clickOnDatabase(this.innerHTML);
            });
        }]
    });
}

function loadTables(dbName) {
    $.ajax({
        type: "GET",
        url: "/databases/" + dbName + "/tables/",
        success: [function (data) {
            clickOnListItem(data, ".tableList", function () {
                clickOnTable(dbName, this.innerHTML);
            });
        }]
    });
}

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
            $(".databaseList").find("tr.selected").removeClass("selected");
            $(".databaseList").find("td:contains(" + dbName + ")").filter(function() {
                return $(this).text() === dbName;
            }).parent().addClass("selected");
            clickOnListItem(data, ".tableList", function () {
                clickOnTable(dbName, this.innerHTML);
            });
        }]
    });
}

function clickOnTable(dbName, tableName) {
    cleanTable(".entriesList");
    cleanImage();

    $.ajax({
        type: "GET",
        url: "/databases/" + dbName + "/tables/" + tableName + "/rows/",
        success: [function (data) {
            $(".tableList").find("tr.selected").removeClass("selected");
            $(".tableList").find("td:contains(" + tableName + ")").filter(function() {
                return $(this).text() === tableName;
            }).parent().addClass("selected");

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

            $(".tableList").find("tr.selected").removeClass("selected");
            $(".tableList").find("td:contains(" + tableName + ")").filter(function() {
                return $(this).text() === tableName;
            }).parent().addClass("selected");
        }]
    })
}

function deleteDatabase() {
    var dbName = $(".databaseList").find("tr.selected > td").text();
    $.ajax({
        type: "DELETE",
        url: "/databases/" + dbName,
        success: function () {
            cleanTable(".databaseList");
            loadDatabases();
        }
    });
}

function deleteTable() {
    var dbName = $(".databaseList").find("tr.selected > td").text();
    var tableName = $(".tableList").find("tr.selected > td").text();
    $.ajax({
        type: "DELETE",
        url: "/databases/" + dbName + "/tables/" + tableName,
        success: function () {
            cleanTable(".tableList");
            loadTables(dbName);
        }
    });
}

function showForm(selector) {
    $(selector).css("display", "block");
}

function hideForm(selector) {
    $(selector).css("display", "none");
}

function cleanTable(selector) {
    $(selector).find("tbody > tr:gt(0)").remove();
}

function cleanImage() {
    $(".image").removeAttr("src");
}



