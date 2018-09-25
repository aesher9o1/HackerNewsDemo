package news.hacker.omnify.hackernews.Threads.Interface;

import java.util.List;

public class CommentFetchWrapper {
    private List<String> userComment, userName, timePosted;

    public CommentFetchWrapper(List<String> userComment, List<String> userName, List<String> timePosted){
        this.userComment = userComment;
        this.userName = userName;
        this.timePosted = timePosted;
    }

    public List<String> getTimePosted() {
        return timePosted;
    }

    public List<String> getUserComment() {
        return userComment;
    }

    public List<String> getUserName() {
        return userName;
    }

}


