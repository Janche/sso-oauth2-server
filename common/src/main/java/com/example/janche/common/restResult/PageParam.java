package com.example.janche.common.restResult;

import com.example.janche.common.utils.CoreUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lirong
 * @ClassName: PageParam
 * @Description: 分页信息的封装
 * @date 2018-11-02 10:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="PageParam",description="分页信息")
public class PageParam {

    @ApiModelProperty(value="当前页码, 默认值为1")
    private Integer page = 1;

    @ApiModelProperty(value="每页尺寸, 默认值为10")
    private Integer size = 10;

    @ApiModelProperty(value="排序字段，默认使用ID来排序")
    private String sortField = "id";

    @ApiModelProperty(value="排序方式，默认降序")
    private String sortOrder = "DESC";

    public String getOrderBy(){
        return CoreUtils.getOrderBy(sortField, sortOrder);
    }

}
