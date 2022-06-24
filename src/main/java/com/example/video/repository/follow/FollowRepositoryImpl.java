package com.example.video.repository.follow;

import com.example.video.entities.Follow;
import com.example.video.entities.QFollow;
import com.example.video.entities.user.QUser;
import com.example.video.repository.BaseRepository;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static com.example.video.util.Util.PAGE_SIZE;

public class FollowRepositoryImpl extends BaseRepository implements FollowRepositoryCustom {
    @Override
    public List<Follow> getFollowList(int page, String name,int ownerId) {
        QFollow qFollow = QFollow.follow;
        QUser qUser = QUser.user;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qFollow.ownerId.eq(ownerId));
        if (StringUtils.isNotBlank(name)){
            builder.and(qUser.name.like("%"+name+"%"));
        }
        return query().from(qFollow)
                .where(builder)
                .innerJoin(qUser).on(qUser.id.eq(qFollow.partnerId))
                .select(qFollow)
                .limit(PAGE_SIZE)
                .offset(PAGE_SIZE*page)
                .fetch();
    }
}
