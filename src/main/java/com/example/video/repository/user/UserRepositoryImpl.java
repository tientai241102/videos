package com.example.video.repository.user;

import com.example.video.entities.constant.UserFilterType;
import com.example.video.entities.user.QUser;
import com.example.video.entities.user.User;
import com.example.video.entities.user.constant.UserRole;
import com.example.video.repository.BaseRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.example.video.util.Util.PAGE_SIZE;


@Repository
class UserRepositoryImpl extends BaseRepository implements UserRepositoryCustom {
    @Override
    public List<User> searchUsers(String name, long ownerId, int page) {
//        QUser qUser = QUser.user;
//        QFollowing qFollowing = QFollowing.following;
//        QUploadFile qUploadFile = QUploadFile.uploadFile;
//
//        BooleanExpression isFollow = JPAExpressions.select(qFollowing.count())
//                .from(qFollowing)
//                .where(qFollowing.partnerId.eq(qUser.id),
//                        qFollowing.ownerId.eq(ownerId))
//                .gt(0L);
//
//        return query().from(qUser)
//                .leftJoin(qUploadFile).on(qUploadFile.id.eq(qUser.avatarId))
//                .where(qUser.phone.like("%" + name + "%").or(qUser.name.like("%" + name + "%")))
//                .select(Projections.fields(User.class,
//                        qUser.id, qUser.name, qUser.phone, isFollow.as("follow"), qUploadFile.as("avatar")))
//                .offset(page*PAGE_SIZE).limit(PAGE_SIZE)
//                .orderBy(qUser.id.desc())
//                .fetch();
        return null;
    }

    @Override
    public List<User> getUsersForAdmin(int page, UserRole role, String name, Date startTime, Date endTime, Boolean deleted, UserFilterType type) {
        QUser qUser = QUser.user;
        BooleanBuilder  builder = new BooleanBuilder();
        builder.and(qUser.phone.like("%" + name + "%").or(qUser.name.like("%" + name + "%")));
        if (role != null){
            builder.and(qUser.role.eq(role));
        }
        builder.and(qUser.deleted.eq(false));
        OrderSpecifier orderSpecifier = qUser.id.desc();
        if (type != null && type.equals(UserFilterType.follow)){
            orderSpecifier = qUser.totalFollower.desc();
        }
        return query().from(qUser)
                .where()
                .select(qUser)
                .offset(page*PAGE_SIZE).limit(PAGE_SIZE)
                .orderBy(orderSpecifier)
                .fetch();
    }

    @Override
    public Optional<User> findUserByName(int userId, String name) {
        return Optional.empty();
    }

    @Override
    public long countUsersForAdmin(UserRole role, String name, Date startTime, Date endTime, Boolean deleted) {
        QUser qUser = QUser.user;
        return query().from(qUser)
                .where(qUser.phone.like("%" + name + "%").or(qUser.name.like("%" + name + "%")))
                .select(qUser)
                .orderBy(qUser.id.desc())
                .fetchCount();
    }
}
