package io.stalk.android.editorial.data;

/**
 * Created by johnkim on 5/8/15.
 */
public class MenuItem {

    private int position;
    private String id;
    private String name;
    private String bkColor;
    private String bkImage;

    public MenuItem() {}

    public MenuItem(int position, String id, String name, String bkColor, String bkImage) {
        this.position = position;
        this.id = id;
        this.name = name;
        this.bkColor = bkColor;
        this.bkImage = bkImage;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBkColor() {
        return bkColor;
    }

    public void setBkColor(String bkColor) {
        this.bkColor = bkColor;
    }

    public String getBkImage() {
        return bkImage;
    }

    public void setBkImage(String bkImage) {
        this.bkImage = bkImage;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "position=" + position +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", bkColor='" + bkColor + '\'' +
                ", bkImage='" + bkImage + '\'' +
                '}';
    }
}
