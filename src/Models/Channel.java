package Models;

/**
 * Created by mattias on 1/11/17.
 * <p>
 * 
 */

@ApiModel(container = "channels", pagination = true)
public class Channel extends IconViewModel {
    private int id;
    private String name;
    private String image;


    @Override
    public String getIconUrl() {
        return image;
    }

    @TableDisplay(visible = false)
    public int getId() {
        return id;
    }

    @TableDisplay(visible = true, order = 1, name = "Program")
    public String getName() {
        return name;
    }

    @TableDisplay(visible = false)
    public String getImage() {
        return image;
    }

    @ApiModelData(type = ApiModelData.CONTENT_TYPES.attribute, name = "id")
    public void setId(int id) {
        this.id = id;
    }

    @ApiModelData(type = ApiModelData.CONTENT_TYPES.attribute, name = "name")
    public void setName(String name) {
        this.name = name;
    }

    @ApiModelData(type = ApiModelData.CONTENT_TYPES.innercontent, name = "image")
    public void setImage(String image) {
        this.image = image;
    }



}
