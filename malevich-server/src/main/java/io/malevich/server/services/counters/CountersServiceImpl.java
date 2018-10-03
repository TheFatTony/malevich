package io.malevich.server.services.counters;


import io.malevich.server.entity.InvolvementEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class CountersServiceImpl implements CountersService {

    private static final String SQL_INVOLVEMENT_QUERY = "select\n" +
            "    max(case when z.property = 'galleries' then value_ end) galleries,\n" +
            "    max(case when z.property = 'members' then value_ end) members,\n" +
            "    max(case when z.property = 'artworks' then value_ end) artworks,\n" +
            "    max(case when z.property = 'art_value' then value_ end) art_value\n" +
            "from\n" +
            "    (select 'galleries' property, count(*) value_ from gallery\n" +
            "    union\n" +
            "    select 'members' property, count(*) value_ from user u join user_roles ur on u.id = ur.user_id where ur.roles = 3\n" +
            "    union\n" +
            "    select 'artworks' property, count(*) value_ from artwork\n" +
            "    union\n" +
            "    select 'art_value' property, sum(price) value_ from artwork) z";

    private static RowMapper<InvolvementEntity> involvementRowMapper = new RowMapper<InvolvementEntity>() {
        @Override
        public InvolvementEntity mapRow(ResultSet resultSet, int i) throws SQLException {
            InvolvementEntity involvementEntity = new InvolvementEntity();
            involvementEntity.setArtValue(resultSet.getDouble("art_value"));
            involvementEntity.setArtworks(resultSet.getDouble("artworks"));
            involvementEntity.setGalleries(resultSet.getDouble("galleries"));
            involvementEntity.setMembers(resultSet.getDouble("members"));
            return involvementEntity;
        }
    };

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public InvolvementEntity getInvolvementCounters() {
        InvolvementEntity data= jdbcTemplate.queryForObject(SQL_INVOLVEMENT_QUERY, involvementRowMapper);
        return data;
    }
}
