package com.tca.utils.constant.interfaces;


import com.tca.utils.constant.finals.EnumManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 有关数据库用于int表示类型或者状态的字段可以写一个对应的ENUM来实现该接口。
 * 实现该接口之后通过自定义 org.apache.ibatis.type.TypeHandler 即可直接在PO中使用ENUM接收数据库中的int类型。
 * @see org.apache.ibatis.type.TypeHandler
 * @see org.apache.ibatis.type.BaseTypeHandler
 * @see GlobalDbTypeEnumHandler
 * @author Devin.qi < devin.qi@ixsecurities.com />
 */
public interface DbTypeEnumIfc extends IndexEnumIfc {

    Logger logger = LoggerFactory.getLogger(DbTypeEnumIfc.class);


    /**
     * @see IndexEnumIfc#getIndex()
     * @see GlobalDbTypeEnumHandler
     * @param clazz
     * @param index
     * @param <T>
     * @return
     */
    static <T extends DbTypeEnumIfc> T parseEnum(Class<T> clazz, int index){
        T dbTypeEnum = EnumManager.getIndexEnumByIndex(index, clazz);
        if (dbTypeEnum == null) {
            logger.debug(clazz + " 类型的Enum列表中没有找到index = " + index + " 的项！");
        }
        return dbTypeEnum;
    }


}
