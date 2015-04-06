package org.cactus.server.transformer;


import org.cactus.share.exception.TypeTransformException;
import org.cactus.share.exception.VOTransformException;
import org.cactus.share.vo.AbstractVO;

public abstract class AbstractTransformer<Type, VO extends AbstractVO>  {

    protected abstract Type populateType(VO vo);
    protected abstract VO populateVO(Type type);

    public Type transform(VO vo) {
        if (vo == null) {
            throw new VOTransformException("VO shouldn't be null");
        }
        return populateType(vo);
    }

    public VO transform(Type type) {
        if (type == null) {
            throw new TypeTransformException("Type shouldn't be null");
        }
        return populateVO(type);
    }

    /*public List<VO> transformListTypes(List<Type> entities) {
        if (entities == null) {
            return new ArrayList<>();
        }
        return entities.stream().map(this::transform).collect(Collectors.toList());
    }

    public List<Type> transformListVO(List<VO> vos) {
        if (vos == null) {
            return new ArrayList<>();
        }
        return vos.stream().map(this::transform).collect(Collectors.toList());
    }*/

}
