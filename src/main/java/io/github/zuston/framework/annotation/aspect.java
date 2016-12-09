package io.github.zuston.framework.annotation;

import java.lang.annotation.*;

/**
 * Created by zuston on 16-12-9.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface aspect {
    Class <? extends Annotation> value();
}
