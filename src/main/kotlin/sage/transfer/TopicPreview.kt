package sage.transfer

import sage.entity.TopicPost
import java.sql.Timestamp

class TopicPreview {
  var id: Long = 0
  var title: String = ""
  var summary: String = ""
  var reference: String = ""
  var author: UserLabel? = null

  var belongTag: TagLabel? = null
  var tags: List<TagLabel> = arrayListOf()

  var whenCreated: Timestamp? = null
  var whenModified: Timestamp? = null
  var whenLastReplied: Timestamp? = null
  var replyCount: Int = 0

  internal constructor() {
  }

  constructor(topic: TopicPost, whenLastReplied: Timestamp?) {
    id = topic.id
    title = topic.title
    summary = topic.content.take(100)
    reference = topic.reference
    author = UserLabel(topic.author)

    belongTag = TagLabel(topic.belongTag)
    tags = topic.tags.map { TagLabel(it) }

    this.replyCount = topic.maxFloorNumber
    whenCreated = topic.whenCreated
    whenModified = actualWhenModified(topic.whenCreated, topic.whenModified)
    this.whenLastReplied = whenLastReplied
  }
}