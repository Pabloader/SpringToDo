// Функция jQuery, по готовности документа делаем много добра
$(document).ready(function() {
    // Функция удаления задачи
    var deleteFunc = function() {
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

    // Обработчик класса content-wrapper, анимация раскрытия списка
    $('.task-list-div>h1').click(function() {
        $(this).siblings('.content-wrapper').slideToggle(600);
    });

    // Обработчик по кнопке удаления задачи
    $('.delete-task-button').click(deleteFunc);

    // Обработчик по кнопке редактирования задачи
    $('.edit-task-button').click(function() {
        var $this = $(this);
        var $taskID = $this.data("id");

        // Обработчик диалогового мордального окна для изменения задачи
        $('#task-dialog').dialog({ width: 500, modal: true, show: "clip" });
        $('#task-dialog #task-target-date').datepicker({dateFormat: 'dd.mm.yy', showButtonPanel: true, showOtherMonths: true, selectOtherMonths: true});

        // Заполняем поля значениями старых параметров
        $('#task-dialog #edit-task-title').val($this.siblings('h1').text());
        $('#task-dialog #edit-task-target-date').val($this.siblings('.width34-form-block').children('.target-date').text());
        $('#task-dialog #edit-task-priority').val($this.siblings('.width34-form-block').children('.priority').text());
        $('#task-dialog #edit-task-content').text($this.siblings('.width64-form-block').text());
        $('#task-dialog #edit-task-completed').attr("checked", $this.siblings('.width34-form-block').children('.completed').text() === "Выполнено!");

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
                    'id':$taskID, 'title': $taskTitle, 'content': $taskContent,
                    'targetTime': $targetDate, 'priority': $taskPriority, 'completed':$taskCompleted,
                    'list': $taskParentList },
                success: function(data) {
                    $('#task-dialog').dialog('close');
                    $this.siblings('h1').html(data.title);
                    $this.siblings('.width34-form-block').children('.target-date').html($.datepicker.formatDate('dd.mm.yy', new Date(data.targetTime)));
                    $this.siblings('.width34-form-block').children('.priority').html(data.priority);
                    $this.siblings('.width34-form-block').children('.completed').html(data.completed ? 'Выполнено!' : 'Не выполнено!');
                    $this.siblings('.width64-form-block').html(data.content);
                    if (data.list.title !== $taskParentList) {
                        $taskBlock = $this.parent();
                        $('.task-list-div[data-list-id=' + data.list.id+']').children('.content-wrapper').append($taskBlock);
                    }
                }
            });

        });
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
                    $insert.append('<h1>' + task.title + '</h1>');
                    $insert.append('<button data-id="' + task.id + '" class="delete-task-button" name="delete-task-button" ><img draggable="false" width="15" height="15" src="/TODO/resources/delete-icon.png"/></button>');
                    $insert.append('<button data-id="' + task.id + '" class="edit-task-button" name="edit-task-button" ><img draggable="false" width="15" height="15" src="/TODO/resources/edit-icon.png"/></button>');
                    $insert.append('<div class="width34-form-block">Автор: <strong>' + task.author.login + '</strong><br/>'
                            + 'Дата создания: ' + $.datepicker.formatDate('dd.mm.yy', new Date(task.creationTime)) + '<br/>'
                            + 'Дата выполнения: ' + $.datepicker.formatDate('dd.mm.yy', new Date(task.targetTime)) + '<br/>'
                            + (task.completed ? 'Выполнено!' : 'Не выполнено!') + '<br/>Приоритет:' + task.priority + '<br/></div>');
                    $insert.append('<div class="width64-form-block">' + task.content + '</div>');
                    $(".task-list-div[data-list-id=" + task.list.id + "]>.content-wrapper").prepend($insert);
                    $('.delete-task-button').click(deleteFunc);
                    // todo $('.edit-task-button').click();
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
