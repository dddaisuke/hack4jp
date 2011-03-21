#!/usr/bin/env python
# coding:utf-8

import string
import random
from datetime import datetime

from google.appengine.ext.webapp import template
from google.appengine.ext import webapp
from google.appengine.ext.webapp import util
import models

__all__ = ('DemaAdd', 'DemaGet', 'DemaCount')


class DemaAdd(webapp.RequestHandler):
    """デマレポート処理"""
    def get(self):
        result = self.request.get('hoge')
        self.response.out.write(result)
        
    def post(self):
        # return demo data
        self.response.headers['Content-Type'] = 'application/json'
        self.response.out.write("""{"status": "success"}""")
        return

        # ユーザ認証
        token = getToken(self.request.get('token'))
        result = ['token=%s' % token]

        tweetid = self.request.get('tweetid')

        # レポート追加
        models.Report(token=token, tweetid=tweetid).put()

        # ツイート更新
        tweet = models.Tweet.all().filter('tweetid =', tweetid).get() or models.Tweet()
        # ツイートID
        tweet.tweetid = tweetid
        # ツイート日時
        tweet.createdat = datetime.fromtimestamp(float(self.request.get('created_at')))
        # 本文
        tweet.tweet = self.request.get('tweet')
        # 発言者のスクリーンネーム
        tweet.screen_name = self.request.get('screen_name')
        # 発言者のユーザ名
        tweet.user_name = self.request.get('user_name')
        # レポート件数
        tweet.count = models.Report.all().filter('tweetid =', tweetid).count()
        # 保存
        tweet.put()
        result.append('%s=%d' % (tweetid, tweet.count))

        # 結果出力
        result = '\n'.join(result)
        self.response.headers["Content-Type"] = "text/plain"
        self.response.headers["Content-Length"] = '%d' % len(result)
        self.response.out.write(result)


class DemaGet(webapp.RequestHandler):
    """デマレポート処理"""
    def get(self):
        tweet_id = int(self.request.get('tweet_id'))

        self.response.headers['Content-Type'] = 'application/json'
        self.response.out.write("""
{"status": "success",
 "tweet_id": %d,
 "text": "東電に勤めてる知り合いによると、いま放射能が大量に出てきて作業している人が逃げ出したらしい",
 "user": {
     "name": "nitoyon",
     "id": 12345,
     "screen_name": "nitoyon",
     "followers_count": 28
 },
 "dema_count": 3,
 "non_dema_count": 2,
 "dema_score": 0.6
}""" % tweet_id)


class DemaCount(webapp.RequestHandler):
    def get(self):
        # ユーザ認証
        token = getToken(self.request.get('token'))
        result = ['token=%s' % token]


class DemaCount(webapp.RequestHandler):
    """デマ件数応答"""
    def get(self):
        # ユーザ認証
        token = getToken(self.request.get('token'))
        result = ['token=%s' % token]

        # ツイートIDごとの件数
        for tweetid in self.request.get_all('tweetid'):
            tweet = models.Tweet.all().filter('tweetid =', tweetid).get()
            result.append('%s=%d' % (tweetid, tweet.count if tweet else 0))

        # 結果出力
        result = '\n'.join(result)
        self.response.headers["Content-Type"] = "text/plain"
        self.response.headers["Content-Length"] = '%d' % len(result)
        self.response.out.write(result)


##  Utility
def save_create_twit(tweet_id, tweet, user,):
    u"""
    tweet_id : Tweet ID
    tweet : Tweet 本文
    user : 投稿者のUserエンティティ
    """
    entity = models.Tweet.get_or_insert(key_name = str(tweet_id),
                                        tweet_id = tweet_id,
                                        tweet = tweet,
                                        user = user)
    return entity

def getToken(token):
    if not token:
        # 新しいトークンを発行
        token = ''.join(random.choice(string.ascii_letters) for i in range(models.User.TOKEN_LENGTH))
        models.User(token=token).put()
    return token


