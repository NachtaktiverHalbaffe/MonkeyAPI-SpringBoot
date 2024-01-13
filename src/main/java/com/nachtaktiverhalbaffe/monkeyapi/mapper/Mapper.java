package com.nachtaktiverhalbaffe.monkeyapi.mapper;

import java.util.List;

public interface Mapper<A, B> {

    B mapTo(A a);

    List<B> mapAllTo(List<A> a);

    A mapFrom(B b);

    List<A> mapFromAll(List<B> b);

}
