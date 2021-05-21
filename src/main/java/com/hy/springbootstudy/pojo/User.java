package com.hy.springbootstudy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("USERS")
public class User {

    @TableId(value = "UIDS",type = IdType.UUID)
    private String uids;
    private String username;
    private String pwd;
}
