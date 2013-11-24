// Функция jQuery, которая собирает данные из формы и посылает их аяксом на сервер
$(document).ready(function() {
    $('#add-task-button').click(function() {
        // Ругаемся громко и задорно
        alert('ЛОЛ ЗАДАЧА-ТО НИХЕРА НЕ ОТПРАВЛЯЕТСЯ!!!');

        // Получаем значения из полей
        var $taskTitle = $('#task-title').val();
        var $taskParentList = $('#task-parent').val();
        var $targetDate = $('#task-target-date').val();
        var $taskPriority = $('#task-priority').val();
        var $taskContent = $('#task-content').val();

        // Task fields: id author list title content creationTime = new Date(); targetTime completed=false; priority
        $.ajax({
            url: 'api/addTask',
            contentType: 'application/json',
            type: 'POST',
            data: {
                'list':null,
                'title':$taskTitle,
                'content':$taskContent,
                'targetTime':$targetDate,
                'priority':$taskPriority
            }
        });
    });
});