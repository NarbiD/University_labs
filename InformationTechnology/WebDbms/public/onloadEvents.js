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
    hideForm(".createRowFormSection");
    loadDatabases();

    $(".createDatabaseForm").on("submit", function () {
        createDatabase($(".databaseName").val());
    });

    function createDatabase(dbName) {
        var outputJson = {};
        outputJson.databaseName = dbName;
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
    }

    $(".createTableForm").on("submit", function () {
        createTable($(".databaseList").find("tr.selected").find("td").text(), $(".tableName").val())
    });

    function createTable(dbName, tblName) {
        var outputJson = parseDataFromTableForm();
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
    }

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

        if($(".databaseList").find("tr.selected").length === 1) {
            showForm(".createTableFormSection");
        }
    });

    $("#btnCreateRow").on("click", function () {
        if($(".tableList").find("tr.selected").length === 1) {
            showForm(".createRowFormSection");
        }
    });

    $("#btnDeleteRow").on("click", function () {
    });

    var image;
    $("input[type=file]").on("change", function(){
        var images = this.files;
        $.each(images, function(key, value) {
            var reader = new FileReader();
            reader.readAsDataURL(value);
            reader.onload = function () {
                if($(".createRowFormSection .textOk").length === 0) {
                    var textOk = $("<span class=\"textOk\">ok</span><br>");
                    textOk.css("color", "green");
                    $("input[type=file]").after(textOk);
                }
                image = reader.result.split(",")[1];
            };
        });
    });

    $(".createRowFormSection .submitButton").on("click", function () {
        sendRow(image);
    });
});

function loadEntries(dbName, tableName) {
    $.ajax({
        type: "GET",
        url: "/databases/" + dbName + "/tables/" + tableName + "/rows/",
        success: [function (data) {
            if (data[0].values !== undefined) {
                $(".entriesList").find("th").attr("colspan", data[0].values.length);
            }
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
            var sortButtons = $("<tr></tr>");
            for (var k = 0; k < data[0].values.length; k++){
                sortButtons.append("<td onclick='sortByColumnNumber(" + k + ", \"" + dbName + "\", \"" + tableName + "\");'>" +
                    "<input type='button' value='sort'></td>");
            }
            cleanImage();
            sortButtons.appendTo(".entriesList > tbody");
        }]
    });
}

function sendRow(image) {
    var outputJson = {};

    outputJson.image = image !== undefined ? image : "";
    outputJson.values = parseDataFromRowForm();

    var dbName = $(".databaseList").find("tr.selected").text();
    var tableName = $(".tableList").find("tr.selected").text();

    $.ajax({
        type: "POST",
        url: "/databases/" + dbName + "/tables/" + tableName + "/rows/",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(outputJson),
        processData: false,
        success: function () {
            hideForm(".createRowFormSection");
            cleanTable(".entriesList");
            loadEntries(dbName, tableName);
        },
        error: function (data) {
            alert(JSON.parse(data.responseText).message.split(":")[1]);
        }
    })
}


function addColumnToTableForm(dataTypes) {
    var columnTypeField = $("<select title=\"type\" class=\"columnType form-control\"></select><br>");
    for(var typeNumber = 0; typeNumber < dataTypes.length; typeNumber++) {
        $("<option value=" + typeNumber + ">" + dataTypes[typeNumber] + "</option>").appendTo(columnTypeField);
    }
    var columnNameField = $("<input type=\"text\" class=\"columnName  form-control\" title=\"column name\"" +
        " placeholder=\"Column name\">");
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
    hideForm(".createDatabaseFormSection");
    hideForm(".createTableFormSection");
    hideForm(".createRowFormSection");

    $.ajax({
        type: "GET",
        url: "/databases/" + dbName + "/tables/",
        success: [function (data) {
            $(".databaseList").find("tr.selected").removeClass("selected");
            $(".databaseList").find("td:contains(" + dbName + ")").filter(function() {
                return $(this).text() === dbName;
            }).parent().addClass("selected");

            cleanTable(".tableList");
            cleanTable(".entriesList");
            cleanImage();

            clickOnListItem(data, ".tableList", function () {
                clickOnTable(dbName, this.innerHTML);
            });
        }]
    });
}

function clickOnTable(dbName, tableName) {
    hideForm(".createTableFormSection");
    hideForm(".createRowFormSection");

    $.ajax({
        type: "GET",
        url: "/databases/" + dbName + "/tables/" + tableName + "/rows/",
        success: [function (data) {
            cleanTable(".entriesList");

            if (data.length !== 0 || data[0] !== undefined) {
                var numberOfColumns = data[0].values.length;
            }


            if (data.length !== 0 || data[0] !== undefined) {
                $(".entriesList").find("th").attr("colspan", numberOfColumns);
            }

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
            var sortButtons = $("<tr></tr>");
            for (var k = 0; k < numberOfColumns; k++){
                sortButtons.append("<td onclick='sortByColumnNumber(" + k + ", \"" + dbName + "\", \"" + tableName + "\");'>" +
                    "<input type='button' value='sort'></td>");
            }
            cleanImage();
            sortButtons.appendTo(".entriesList > tbody");

            $(".tableList").find("tr.selected").removeClass("selected");
            $(".tableList").find("td:contains(" + tableName + ")").filter(function() {
                return $(this).text() === tableName;
            }).parent().addClass("selected");
        }]
    });

    $.ajax({
        type: "GET",
        url: "/databases/" + dbName + "/tables/" + tableName + "/",
        success: [function (data) {
            var names = data.columnNames;
            initRowForm(names);
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
            cleanTable(".tableList");
            cleanTable(".entriesList");
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

function initRowForm(columnNames) {

    $(".createRowFormSection .fields").html("");
    addRowsToForm(columnNames);
}

function addRowsToForm(columnNames) {
    for (var i = 0; i < columnNames.length; i++) {
        var columnNameField = $("<b class=\"columnNameField\"></b>");
        columnNameField.text(columnNames[i] + ": ");
        var columnValueField = $("<input type=\"text\" class=\"columnValueField form-control\" title=\"value field\">");
        var fieldPair = $("<div class=\"rowField\"></div><br>");
        fieldPair.append(columnNameField, columnValueField);
        $(".createRowFormSection .fields").append(fieldPair);
    }
}

function parseDataFromRowForm() {
    var fieldsFromForm = $(".createRowFormSection .columnValueField");
    var values = new Array(fieldsFromForm.length);
    for (var i = 0; i < fieldsFromForm.length; i++) {
        values[i] = fieldsFromForm.eq(i).val();
    }
    return values;
}

function sortByColumnNumber(colNumber, dbName, tableName) {
    $.ajax({
        type: "GET",
        url: "/databases/" + dbName + "/tables/" + tableName + "/rows/",
        success: [function (data) {
            cleanTable(".entriesList");

            data.sort(function (a, b) {
                return a.values[colNumber] > b.values[colNumber];
            });

            var rows = _buildRows();
            var sortButtons = _buildSortButtons();
            var table = $(".entriesList > tbody");
            cleanImage();
            _resizeHeader(data[0].values.length);
            table.append(rows);
            table.append(sortButtons);
        }]
    });

    function _resizeHeader(size) {
        if (data[0].values !== undefined) {
            $(".entriesList").find("th").attr("colspan", size);
        }
    }

    function _buildRows() {
        var rows = $("");
        for (var k = 0; k < data.length; k++) {
            var values = data[k].values;
            var row = $("<tr></tr>");
            for (var j = 0; j < values.length; j++){
                $("<td>" + values[j] + "</td>").appendTo(row).on("click", function () {
                    var s = $(this).parent('tr').index();
                    $(".image").attr("src", "data:image/png;base64," + data[s-1].image);
                });
            }
            rows.append(row);
        }
        return rows;
    }

    function _buildSortButtons() {
        var sortButtons = $("<tr></tr>");
        for (var q = 0; q < data[0].values.length; q++){
            sortButtons.append("<td onclick='sortByColumnNumber(" + q + ", \"" + dbName + "\", \"" + tableName + "\");'>" +
                "<input type='button' class='' value='sort'></td>");
        }
        return sortButtons;
    }
}
