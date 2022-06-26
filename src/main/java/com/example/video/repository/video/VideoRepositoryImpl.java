package com.example.video.repository.video;

import com.example.video.entities.QFollow;
import com.example.video.entities.constant.VideoFilterType;
import com.example.video.entities.user.QUser;
import com.example.video.entities.video.QVideo;
import com.example.video.entities.video.Video;
import com.example.video.entities.video.constant.VideoCategory;
import com.example.video.repository.BaseRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.video.util.Util.PAGE_SIZE;

@Repository
public class VideoRepositoryImpl extends BaseRepository implements VideoRepositoryCustom {
    @Override
    public List<Video> getVideos(int page, String name, Integer ownerId, VideoFilterType type, VideoCategory category) {
        QVideo qVideo = QVideo.video1;
        BooleanBuilder builder = new BooleanBuilder();
        if (ownerId != null){
            builder.and(qVideo.ownerId.eq(ownerId));
        }
        builder.and(qVideo.deleted.eq(false));
        if (StringUtils.isNotBlank(name)){
            builder.and(qVideo.title.like("%"+name+"%"));
        }
        if (category != null){
            builder.and(qVideo.category.eq(category));
        }
        builder.and(qVideo.deleted.eq(false));
        OrderSpecifier orderSpecifier = qVideo.id.desc();
        if (type != null && type.equals(VideoFilterType.view)){
            orderSpecifier = qVideo.numberView.desc();
        }else if (type != null && type.equals(VideoFilterType.rate)){
            orderSpecifier= qVideo.rateAVG.desc();
        }

        return query().from(qVideo)
                .where(builder)
                .select(qVideo)
                .limit(PAGE_SIZE)
                .offset(PAGE_SIZE*page)
                .orderBy(orderSpecifier)
                .fetch();
    }

    @Override
    public long countVideos(String name, Integer ownerId, VideoFilterType type, VideoCategory category) {
        QVideo qVideo = QVideo.video1;
        BooleanBuilder builder = new BooleanBuilder();
        if (ownerId != null){
            builder.and(qVideo.ownerId.eq(ownerId));
        }
        if (category != null){
        builder.and(qVideo.category.eq(category));
        }
        if (StringUtils.isNotBlank(name)){
            builder.and(qVideo.title.like("%"+name+"%"));
        }
        builder.and(qVideo.deleted.eq(false));
        return query().from(qVideo)
                .where(builder)
                .select(qVideo)
                .fetchCount();
    }
}
