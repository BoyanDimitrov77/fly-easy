CREATE TABLE IF NOT EXISTS picture(
id VARCHAR(36) NOT NULL DEFAULT '',
original_picture_resource_id VARCHAR(36) DEFAULT NULL,
thumbnail_picture_resource_id VARCHAR(36) DEFAULT NULL,
full_screen_picture_resource_id VARCHAR(36) DEFAULT NULL,
PRIMARY KEY(id)
,
CONSTRAINT picuture_resource_original_picture FOREIGN KEY (original_picture_resource_id) REFERENCES resources(id),
CONSTRAINT picuture_resource_thumbnail_picture FOREIGN KEY (thumbnail_picture_resource_id) REFERENCES resources(id),
CONSTRAINT picuture_resource_full_screen_picture FOREIGN KEY (full_screen_picture_resource_id) REFERENCES resources(id)

);

CREATE INDEX picuture_resource_original_picture ON picture (original_picture_resource_id);
CREATE INDEX picuture_resource_thumbnail_picture ON picture (thumbnail_picture_resource_id);
CREATE INDEX picuture_resource_full_screen_picture ON picture (full_screen_picture_resource_id);