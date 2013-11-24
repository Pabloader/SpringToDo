// Функция jQuery, которая собирает данные из формы и посылает их аяксом на сервер
$(document).ready(function() {
    // Установка календаря jQuery UI для поля ввода даты
    $('#task-target-date').datepicker({dateFormat:'dd.mm.yy',showButtonPanel:true, showOtherMonths:true, selectOtherMonths:true});
    // Обработчик класса content-wrapper
    $('.task-list-div>h1').click(function () {
        $(this).siblings('.content-wrapper').slideToggle(600);
    });
    // Обработчик по кнопке удаления задачи
    $('.delete-task-button').click(function() {
        var $this = $(this);
        var $taskID = $this.data("id");
        $.ajax({
            url: 'api/deleteTask',
            type: 'GET',
            data: {
                'id': $taskID
            },
            success: function() {                
                $this.parent().hide('fold', 400).remove();
            }
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
            url: 'api/addTask',
            type: 'POST',
            data: {
                'list': $taskParentList,
                'title': $taskTitle,
                'content': $taskContent,
                'targetTime': $targetDate,
                'priority': $taskPriority
            },
            success: function(data) {
                alert("Задача добавлена! Может быть...");
            }
        });
    });
});