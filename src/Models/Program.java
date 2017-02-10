package Models;

/**
 * Created by mattias on 1/11/17.
 */
@ApiModel(container = "program")
public class Program extends IconViewModel {
    private int id;
    private String name;
    private String broadcastinfo;
    private String programimage;
    private String description;


    @Override
    public String getIconUrl() {
        return programimage;
    }

    @TableDisplay(columnName = "Program")
    public String getName() {
        return name;
    }

    public String getBroadcastinfo() {
        return broadcastinfo;
    }
    public String getProgramimage() {
        return programimage;
    }
    public int getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }

    @ApiModelData(type = ApiModelData.CONTENT_TYPES.innercontent, name = "broadcastinfo")
    public void setBroadcastinfo(String broadcastinfo) {
        this.broadcastinfo = broadcastinfo;
    }
    @ApiModelData(type = ApiModelData.CONTENT_TYPES.innercontent, name = "programimage")
    public void setProgramimage(String programimage) {
        this.programimage = programimage;
    }
    @ApiModelData(type = ApiModelData.CONTENT_TYPES.attribute, name = "id")
    public void setId(int id) {
        this.id = id;
    }
    @ApiModelData(type = ApiModelData.CONTENT_TYPES.attribute, name = "columnName")
    public void setName(String name) {
        this.name = name;
    }
    @ApiModelData(type = ApiModelData.CONTENT_TYPES.innercontent, name = "description")
    public void setDescription(String description) {
        this.description = description;
    }


}
