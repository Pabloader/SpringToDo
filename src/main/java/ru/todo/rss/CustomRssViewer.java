package ru.todo.rss;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;
import com.sun.syndication.feed.rss.Channel;
import com.sun.syndication.feed.rss.Content;
import com.sun.syndication.feed.rss.Item;
import ru.todo.model.TodoTask;

public class CustomRssViewer extends AbstractRssFeedView {

    @Override
    protected void buildFeedMetadata(Map<String, Object> model, Channel feed,
                                     HttpServletRequest request) {

        feed.setTitle("Spring TODO application");
        feed.setLink("http://localhost:8080/TODO");
        feed.setDescription("Spring TODO application");

        super.buildFeedMetadata(model, feed, request);
    }

    @Override
    protected List<Item> buildFeedItems(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        List<TodoTask> todoList = (List<TodoTask>) model.get("todoList");

        List<Item> items = new ArrayList<Item>();

        for (TodoTask task : todoList) {

            Item item = new Item();

            Content content = new Content();
            content.setValue(task.getContent());
            item.setContent(content);

            item.setTitle(task.getTitle());
            item.setPubDate(task.getCreationTime());

            items.add(item);
        }

        return items;
    }

}
