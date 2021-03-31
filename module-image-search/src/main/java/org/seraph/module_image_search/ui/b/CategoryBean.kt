package org.seraph.module_image_search.ui.b

data class CategoryBean(
    var data: List<CategoryDataBean>,
)

/**
 * {
"_id": "5e959250808d6d2fe6b56eda",
"author": "鸢媛",
"category": "Girl",
"createdAt": "2020-05-25 08:00:00",
"desc": "与其安慰自己平凡可贵，\n不如拼尽全力活得漂亮。 ​ ​​​​",
"images": [
"http://gank.io/images/f4f6d68bf30147e1bdd4ddbc6ad7c2a2"
],
"likeCounts": 7,
"publishedAt": "2020-05-25 08:00:00",
"stars": 1,
"title": "第96期",
"type": "Girl",
"url": "http://gank.io/images/f4f6d68bf30147e1bdd4ddbc6ad7c2a2",
"views": 14593
}
 */
data class CategoryDataBean(
    var _id: String,
    var author: String,
    var category: String,
    var createdAt: String,
    var desc: String,
    var images: List<String>,
    var likeCounts: String,
    var publishedAt: String,
    var stars: String,
    var title: String,
    var type: String,
    var url: String,
    var views: String
)
