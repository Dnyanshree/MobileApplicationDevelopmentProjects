package example.com.inclass07app;

/*
* Assignment: InClass07
* Filename: Story.java
* Full names of group members:
*   Kedar Vijay Kulkarni
*   Dnyanshree Shengulwar
*   Marissa McLaughlin
* */
public class Story {
    private String storyTitle, storyByline, storyAbstract, storyCreatedDate, storyThumbImageUrl,storyNormalImageUrl;


    public String getStoryTitle() {
        return storyTitle;
    }

    public void setStoryTitle(String storyTitle) {
        this.storyTitle = storyTitle;
    }

    public String getStoryByline() {
        return storyByline;
    }

    public void setStoryByline(String storyByline) {
        this.storyByline = storyByline;
    }

    public String getStoryAbstract() {
        return storyAbstract;
    }

    public void setStoryAbstract(String storyAbstract) {
        this.storyAbstract = storyAbstract;
    }

    public String getStoryCreatedDate() {
        return storyCreatedDate;
    }

    public void setStoryCreatedDate(String storyCreatedDate) {
        this.storyCreatedDate = storyCreatedDate;
    }

    public String getStoryThumbImageUrl() {
        return storyThumbImageUrl;
    }

    public void setStoryThumbImageUrl(String storyThumbImageUrl) {
        this.storyThumbImageUrl = storyThumbImageUrl;
    }

    public String getStoryNormalImageUrl() {
        return storyNormalImageUrl;
    }

    public void setStoryNormalImageUrl(String storyNormalImageUrl) {
        this.storyNormalImageUrl = storyNormalImageUrl;
    }

    @Override
    public String toString() {
        return "Story{" +
                "storyTitle='" + storyTitle + '\'' +
                ", storyByline='" + storyByline + '\'' +
                ", storyAbstract='" + storyAbstract + '\'' +
                ", storyCreatedDate='" + storyCreatedDate + '\'' +
                ", storyThumbImageUrl='" + storyThumbImageUrl + '\'' +
                ", storyNormalImageUrl='" + storyNormalImageUrl + '\'' +
                '}';
    }
}
