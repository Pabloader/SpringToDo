// Функция jQuery, по готовности документа делаем много добра
$(document).ready(function() {
    // Функция удаления задачи
    var deleteTaskFunc = function() {
        var $this = $(this);
        var $taskID = $this.data("id");
        $.ajax({
            url: 'api/deleteTask', type: 'GET',
            data: {'id': $taskID},
            success: function(data) {
                if (data === "success")
                    $this.parent().remove();
                else
                    alert("Ошибка при удалении записи: " + data + "!");
            },
            error: function() {
                alert("Ошибка при удалении записи!");
            }
        });
    };

    // Функция удаления списка
    var deleteListFunc = function() {
        var $this = $(this);
        var $listID = $this.data("id");
        $.ajax({
            url: 'api/deleteList', type: 'GET',
            data: {'id': $listID},
            success: function(data) {
                if (data === "success")
                {
                    $this.parent().remove();
                    // Модифицируем комбобоксы
                    $('#task-parent, #edit-task-parent').children('option[value=' + $listID + "]").remove();
                }
                else
                    alert("Ошибка при удалении списка: " + data + "!");
            },
            error: function() {
                alert("Ошибка при удалении списка!");
            }
        });
    };

    //Функция редактирования задачи

    var editTaskFunc = function() {
        var $this = $(this);
        var $taskID = $this.data("id");

        // Обработчик диалогового мордального окна для изменения задачи
        $('#task-dialog').dialog({width: 500, modal: true, show: "clip"});

        // Заполняем поля значениями старых параметров
        $('#edit-task-title').val($this.siblings('h1').text());
        $('#edit-task-target-date').val($this.siblings('.width34-form-block').children('.target-date').text());
        $('#edit-task-priority').val($this.siblings('.width34-form-block').children('.priority').text());
        $('#edit-task-parent').val($this.parent().parent().parent().data('list-id'));
        $('#edit-task-content').text($this.siblings('.width64-form-block').text());
        $('#edit-task-completed').attr("checked", $this.siblings('.width34-form-block').children('.completed').text() === "Выполнено!");

        // Вешаем обработчик на клик по кнопке обновления задачи
        $('#edit-send-button').click(function() {
            var $taskTitle = $('#edit-task-title').val();
            var $taskParentList = $('#edit-task-parent').val();
            var $targetDate = $('#edit-task-target-date').val();
            var $taskPriority = $('#edit-task-priority').val();
            var $taskContent = $('#edit-task-content').val();
            var $taskCompleted = $('#edit-task-completed').prop('checked');
            $.ajax({
                url: 'api/editTask', type: 'POST',
                data: {
                    'id': $taskID, 'title': $taskTitle, 'content': $taskContent,
                    'targetTime': $targetDate, 'priority': $taskPriority, 'completed': $taskCompleted,
                    'list': $taskParentList},
                success: function(data) {
                    $('#task-dialog').dialog('close');
                    $this.siblings('h1').html(data.title);
                    $this.siblings('.width34-form-block').children('.target-date').html($.datepicker.formatDate('dd.mm.yy', new Date(data.targetTime)));
                    $this.siblings('.width34-form-block').children('.priority').html(data.priority);
                    $this.siblings('.width34-form-block').children('.completed').html(data.completed ? 'Выполнено!' : 'Не выполнено!');
                    $this.siblings('.width64-form-block').html(data.content);
                    if (data.list.title !== $taskParentList) {
                        var $taskBlock = $this.parent();
                        $('.task-list-div[data-list-id=' + data.list.id + ']').children('.content-wrapper').append($taskBlock);
                    }
                }
            });
        });
    };

    //Функция редактирования списка

    var editListFunc = function() {
        var $this = $(this);
        var $listID = $this.data("id");

        // Обработчик диалогового мордального окна для изменения задачи
        $('#edit-list-dialog').dialog({width: 500, modal: true, show: "clip"});

        // Заполняем поля значениями старых параметров
        $('#edit-list-title').val($this.siblings('h1').text());

        // Вешаем обработчик на клик по кнопке обновления задачи
        $('#edit-list-send-button').click(function() {
            var $listTitle = $('#edit-list-title').val();
            var $listPubStatus = $('#edit-list-pub-status').val();
            $.ajax({
                url: 'api/editList', type: 'POST',
                data: {
                    'id': $listID, 'title': $listTitle,
                    'pubStatus': $listPubStatus
                },
                success: function(list) {
                    $('#edit-list-dialog').dialog('close');
                    $this.siblings('h1').html(list.title);
                    // Модифицируем комбобоксы
                    $('#task-parent, #edit-task-parent').children('option[value=' + list.id + "]").text(list.title);
                }
            });
        });
    };

    // Установка региона календаря jQuery UI для поля ввода даты
    $.datepicker.regional['ru'] = {
        closeText: 'Закрыть',
        prevText: '&#x3c;Пред',
        nextText: 'След&#x3e;',
        currentText: 'Сегодня',
        monthNames: ['Январь', 'Февраль', 'Март', 'Апрель', 'Май', 'Июнь',
            'Июль', 'Август', 'Сентябрь', 'Октябрь', 'Ноябрь', 'Декабрь'],
        monthNamesShort: ['Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн',
            'Июл', 'Авг', 'Сен', 'Окт', 'Ноя', 'Дек'],
        dayNames: ['воскресенье', 'понедельник', 'вторник', 'среда', 'четверг', 'пятница', 'суббота'],
        dayNamesShort: ['вск', 'пнд', 'втр', 'срд', 'чтв', 'птн', 'сбт'],
        dayNamesMin: ['Вс', 'Пн', 'Вт', 'Ср', 'Чт', 'Пт', 'Сб'],
        dateFormat: 'dd.mm.yy',
        firstDay: 1,
        isRTL: false
    };
    $.datepicker.setDefaults($.datepicker.regional['ru']);
    // Устанавливаем календарь на поле ввода даты задачи
    $('#task-target-date').datepicker({
        dateFormat: 'dd.mm.yy', showButtonPanel: true,
        showOtherMonths: true, selectOtherMonths: true});
    $('#edit-task-target-date').datepicker({
        dateFormat: 'dd.mm.yy', showButtonPanel: true,
        showOtherMonths: true, selectOtherMonths: true});
    // Устанавливаем спиннер на приоритет задачи
    $('#task-priority').spinner();
    $('#edit-task-priority').spinner();
    // Обработчик класса content-wrapper, анимация раскрытия списка
    $('.task-list-div>h1').click(function() {
        $(this).siblings('.content-wrapper').slideToggle(600);
    });

    // Обработчик по кнопке удаления задачи
    $('.delete-task-button').click(deleteTaskFunc);

    // Обработчик по кнопке редактирования задачи
    $('.edit-task-button').click(editTaskFunc);

    $('.delete-list-button').click(deleteListFunc);
    $('.edit-list-button').click(editListFunc);

    // Обработчик по кнопке создания списка, показать диалог
    $('#add-list-button').click(function() {
        $('#add-list-title').val('');
        $('#add-list-pub-status').val(0);
        $('#add-list-dialog').dialog({width: 500, modal: true, show: "clip"});
    });

    // Обработчик на кнопку добавления задач
    $('#add-task-button').click(function() {
        var $taskTitle = $('#task-title').val();
        var $taskParentList = $('#task-parent').val();
        var $targetDate = $('#task-target-date').val();
        var $taskPriority = $('#task-priority').val();
        var $taskContent = $('#task-content').val();
        $.ajax({
            url: 'api/addTask', type: 'POST',
            data: {
                'list': $taskParentList, 'title': $taskTitle,
                'content': $taskContent, 'targetTime': $targetDate,
                'priority': $taskPriority
            },
            success: function(task) {
                if (task) {
                    var $insert = $('<div class="page-block task-block"/>');
                    $('<h1>').text(task.title).appendTo($insert);
                    $insert.append('<button data-id="' + task.id + '" class="delete-task-button" name="delete-task-button" ><img draggable="false" width="15" height="15" src="/TODO/resources/delete-icon.png"/></button>');
                    $insert.append('<button data-id="' + task.id + '" class="edit-task-button" name="edit-task-button" ><img draggable="false" width="15" height="15" src="/TODO/resources/edit-icon.png"/></button>');
                    $insert.append('<div class="width34-form-block">Автор: <strong>' + task.author.login + '</strong><br/>'
                            + 'Дата создания: ' + $.datepicker.formatDate('dd.mm.yy', new Date(task.creationTime)) + '<br/>'
                            + 'Дата выполнения: ' + $.datepicker.formatDate('dd.mm.yy', new Date(task.targetTime)) + '<br/>'
                            + (task.completed ? 'Выполнено!' : 'Не выполнено!') + '<br/>Приоритет:' + task.priority + '<br/></div>');
                    $('<div class="width64-form-block">').text(task.content).appendTo($insert);
                    $(".task-list-div[data-list-id=" + task.list.id + "]>.content-wrapper").prepend($insert);

                    //Обновляем обработчики нажатий кнопок
                    $('.delete-task-button').click(deleteTaskFunc);
                    $('.edit-task-button').click(editTaskFunc);
                }
                else
                    alert("Ошибка при добавлении записи!");
            },
            error: function() {
                alert("Ошибка при добавлении записи!");
            }
        });
    });

    $('#add-list-send-button').click(function() {
        var $listTitle = $('#add-list-title').val();
        var $listPubStatus = $('#add-list-pub-status').val();
        $.ajax({
            url: 'api/addList', type: 'POST',
            data: {
                'title': $listTitle, 'pubStatus': $listPubStatus
            },
            success: function(list) {
                if (list) {
                    $("#add-list-dialog").dialog("close");
                    var $insert = $('<div class="task-list-div" data-list-id="' + list.id + '"/>');
                    $('<h1>').text(list.title).click(function() {
                        $(this).siblings('.content-wrapper').slideToggle(600);
                    }).appendTo($insert);
                    $insert.append('<button data-id="' + list.id + '" class="delete-list-button"><img draggable="false" width="15" height="15" src="/TODO/resources/delete-icon.png"/></button>');
                    $insert.append('<button data-id="' + list.id + '" class="edit-list-button"><img draggable="false" width="15" height="15" src="/TODO/resources/edit-icon.png"/></button>');
                    $('<div class="content-wrapper"/>').appendTo($insert);
                    $("#lists-wrapper").append($insert);

                    // Модифицируем комбобоксы
                    $('#task-parent, #edit-task-parent').append($('<option>').attr("value", list.id).text(list.title));

                    //Обновляем обработчики нажатий кнопок
                    $('.delete-list-button').click(deleteListFunc);
                    $('.edit-list-button').click(editListFunc);
                }
                else
                    alert("Ошибка при добавлении записи!");
            },
            error: function() {
                alert("Ошибка при добавлении записи!");
            }
        });
    });
});
