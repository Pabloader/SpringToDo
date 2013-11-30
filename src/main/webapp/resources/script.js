// Функция jQuery, по готовности документа делаем много добра
$(document).ready(function() {
    // Установка календаря jQuery UI для поля ввода даты
    $('#task-target-date').datepicker({dateFormat: 'dd.mm.yy', showButtonPanel: true, showOtherMonths: true, selectOtherMonths: true});

    // Обработчик класса content-wrapper
    $('.task-list-div>h1').click(function() {
        $(this).siblings('.content-wrapper').slideToggle(600);
    });

    // Обработчик по кнопке удаления задачи
    $('.delete-task-button').click(function() {
        var $this = $(this);
        var $taskID = $this.data("id");
        $.ajax({
            url: 'api/deleteTask', type: 'GET',
            data: {'id': $taskID},
            success: function() {
                $this.parent().remove();
            }
        });
    });

    // Обработчик по кнопке редактирования задачи
    $('.edit-task-button').click(function() {
        var $this = $(this);
        var $taskID = $this.data("id");

        // Обработчик диалогового мордального окна для изменения задачи
        $('#task-dialog').dialog({ width: 500, modal: true });
        $('#task-dialog #task-target-date').datepicker({dateFormat: 'dd.mm.yy', showButtonPanel: true, showOtherMonths: true, selectOtherMonths: true});

        // Заполняем поля значениями старых параметров
        $('#task-dialog #edit-task-title').attr('value', $this.siblings('h1').html());
        $('#task-dialog #edit-task-target-date').attr('value', $this.siblings('.width34-form-block').children('.target-date').html());
        $('#task-dialog #edit-task-priority').attr('value', $this.siblings('.width34-form-block').children('.priority').html());
        $('#task-dialog #edit-task-content').html($this.siblings('.width64-form-block').html());

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
                    $this.siblings('h1').html(data.title);
                    $this.siblings('.width34-form-block').children('.target-date').html($.datepicker.formatDate('dd.mm.yy', new Date(data.targetTime)));
                    $this.siblings('.width34-form-block').children('.priority').html(data.priority);
                    $this.siblings('.width34-form-block').children('.completed').html(data.completed ? 'Выполнено!' : 'Не выполнено!');
                    $this.siblings('.width64-form-block').html(data.content);
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
            success: function(data) {
                var $insert = $('<div class="page-block task-block"></div>');
                $insert.append('<h1>' + data.title + '</h1>');
                $insert.append('<button data-id="' + data.id + '" class="delete-task-button" name="delete-task-button" ><img draggable="false" width="15" height="15" src="/TODO/resources/delete-icon.png"/></button>');
                $insert.append('<div class="width34-form-block">Автор: <strong>' + data.author.login + '</strong><br/>'
                        + 'Дата создания: ' + $.datepicker.formatDate('dd.mm.yy', new Date(data.creationTime)) + '<br/>'
                        + 'Дата выполнения: ' + $.datepicker.formatDate('dd.mm.yy', new Date(data.targetTime)) + '<br/>'
                        + (data.completed ? 'Выполнено!' : 'Не выполнено!') + '<br/>Приоритет:' + data.priority + '<br/></div>');
                $insert.append('<div class="width64-form-block">' + data.content + '</div>');
                var $listID = data.list.id;
                $(".task-list-div[data-list-id=" + $listID + "]>.content-wrapper").prepend($insert);
                $('.delete-task-button').click(function() {
                    var $this = $(this);
                    var $taskID = $this.data("id");
                    $.ajax({
                        url: 'api/deleteTask', type: 'GET',
                        data: {'id': $taskID},
                        success: function() {
                            $this.parent().remove();
                        }
                    });
                });
            }
        });
    });
});
