### git config###

- git --version

  _git版本号_

- git config --list

  _查看一些配置（全局），到本地仓库目录下查看的是当前仓库的配置_

- git config --global user.name '用户名'

  _设置全局用户名，user.email全局邮箱_

  *--local或者什么也不写，设置当前仓库的用户名，邮箱*

- git log

  *查看提交记录*

- git status

  *查看内容修改信息* 

- git config --global alias.st status

  *为status设置别名st*

- git config --global pull.rebase true

- cat ~/.gitconfig

  *查看全局的配置文件内容*

- git config --list --global

  *查看全局git配置。读取的就是全局的git配置文件.gitconfig*

- git config --list --local

  *查看当前仓库的配置。读取的是当前仓库目录下的.git/config文件*

- system级别，global级别，local级别

### git clone

- git clone 地址

  _默认克隆master分支_

- git branch

  _查看当前分支_

- git clone -b dev 地址

  _克隆指定分支，这个表示克隆dev分支_

### git tag

- git tag

  _查看所有标签_

- git tag -a v0.0.1 -m '信息'

  _打标签，一般信息为v0.0.1_

- git push --tag(两个-)

  _标签推送到远程_

- git tag -l 'v0.0.*'

  _查找以v0.0.开始的版本号_

- git log --pretty=online

  _查看提交记录_

- git tag -a v0.0.6 提交id -m '信息'

  _在指定提交后打标签_


### git status

- _查看文件的更新_

  - _已添加至暂存区，待提交（git add后）_
  - _已修改，为暂存_
  - _未追踪状态的文件_
    - _新添加的文件是未追踪的文件_
    - _修改和删除是未添加到暂存区的文件_
      - _新增test3，删除test2，修改test_
      - _git add test，test添加到暂存区_
      - _此时，test3未追踪，test2未添加到暂存区，test在暂存区等待被提交_
      - _如果此时提交，则此时只显示test2和test3文件，test1被提交不再显示_
      - _如果test添加到暂存区后不提交，然后直接修改test文件，则test会存在两个地方有状态，提交至暂存区和未提交至暂存区_

- git add test

  _将test添加到暂存区_

- git commit -m '信息'

  _提交暂存区的内容_

- git add -A

  _将所有内容添加至暂存区_

### git diff

- _查看文件具体的修改内容_（默认查看没有添加到暂存区的内容，所有修改过的文件）

  - _+表示新增的内容_
  - _-表示删除的内容_

- git diff --staged

  _查看添加到暂存区的内容的修改_

### git commit message

- git log --pretty=oneline

  _列出提交记录，并显示提交信息_

- git log --pretty=oneline | grep fix

  _过滤内容，查找带有指定信息的提交记录_

- commit message包括三部分，主要使用Header部分

  - Header
  - Body
  - Footer

- Header部分包括，type(scope): subject（注意冒号后面有一个空格）

- type类型

  - feat

    _新功能_

  - fix

    _修复bug_

  - style

    _格式_

  - refactor

    _代码重构_

  - chore

    _项目构建_

- scope

  _修改代码针对的内容，模块_

- subject

  _描述信息_

- git reset --hard 提交记录id

  _撤销提交_

- git push origin master --force

  _提交到远程仓库master分支_

- git cz

  _commitizen，支持angular格式的Commit message_

  _commitizen init cz-conventional-changelog--save-exact_

- git init

  _初始化_

- change log

  _npm install -g conventional-changelog_

  _conventional-changelog-p angular -i CHANGELOG.md -s -r 0_

  _默认情况下只有type为feat和fix的提交信息才会记录到CHANGELOG文件中_

- git commit -m 'fix(stockout2): 修复拖拽bug'

### 分支管理

- git branch

  _展示现有的分支，当前分支前面带有*_

- git branch hotfix-406

  _创建一个新分支，名为hotfix-406_

- git checkout hotfix-406

  _切换分支，切换到hotfix-406_

- git checkout -b self

  _创建self分支，并且切换到self分支_

- git branch -d hotfix-406

  _删除hotfix-406分支_

- git merge self

  _当前分支为dev，将self的内容融入到dev（dev中增加了self的代码）_

- CONFLICT

  _发生冲突关键字_，查看冲突文件，内容会有冲突标记

### 远程抓取

- git fetch

  _取得远程最新版本，不更新本地仓库_

  _git fetch origin dev_

- git merge

  _将远程提交的内容更新到本地_

- git pull = git fetch + git merge

  _git pull origin dev_

### 远程推送

- git push 分支

  _git push origin dev_

- git push -d

  _删除分支_

  _git push origin -d self_

- git push --tag

  _推送tag_

### git remote

- git remote -v

  _给出远程仓库位置，哪些可以fetch，哪些可以push_

- git remote add

  _为本地仓库创建远程连接，有远程仓库了也可以再指向一个_

  _git remote add origin2 远程仓库地址，注意此时不能使用origin了，因为第一个远程仓库默认使用了origin_

- git remote remove

  _删除远程连接，git remote remove origin2_

  _本地仓库连接一个新的远程仓库，推送一部分信息，然后删除连接，新的远程仓库此时就可以在其他地方建立一个本地仓库了，并且带有一定的信息_

### git merge

 _合并_

_将远程（origin）提交合并到本地，会将远程和本地的提交后面生成一个新的提交_

发生冲突会将所有冲突文件列出来，手动添加提交

- git fetch origin dev

  _当前分支test，抓取远程dev分支版本_

- git merge dev

  _当前分支test，将dev分支内容合并到test_

- git push origin test

  _推送到远程_

### git rebase

_变基 ，衍合_

_将远程提交合并到本地，本地的提交会生成副本拼接到远程提交的后面_

发生冲突只会提示一个冲突，add，git rebase --continue，不用提交

- git rebase --continue

  _解决一个冲突后继续衍合_

- git fetch origin dev

  _当前分支test，抓取远程dev分支版本_

- git rebase dev

  _合并_

- git push origin test --force

  _推送到远程_

### git撤销

#### commit之前的撤销

- 未添加到暂存区的撤销（没执行add操作）

  - git checkout -- test2

    _撤销对test2文件的修改_

  - git checkout -- .（杠杠点）

    _对所有修改文件进行撤销_

- 添加到暂存区的撤销（执行过add操作）

  - git reset HEAD test2

    _test2从暂存区撤销，变为未提交至暂存区状态（文件仍然是有改动）_

  - git reset HEAD

    _将所有文件从暂存区撤销，变为未提交至暂存区状态_

####提交（commit）之后的撤销

- git revert 提交id

  _撤销这次提交_

  _偶数次revert后，后恢复到第一次revert之前，表示撤销上一次撤销_

#### git回滚

_回退到某次提交，该提交以后的提交都回退_

- git reset -- hard  commit-id

  _回退到某次提交_

- git ps origin dev --force

  _强制推送到远程（覆盖），不可逆_

### 本地项目创建

- 创建项目

- git配置

- 初始化成git项目

  - git init

- 添加.gitignore文件夹

- 添加ReadME文件

- git remote add origin 仓库地址

  _将本地仓库连接到远程仓库_

- git remote -v

  _查看从哪个仓库fetch和push_

- 使用add，commit，push将本地仓库内容推送到远程

### 正常开发联调

- 建立个人分支

- 文件新增，编辑，删除

- 状态查看git status

- 添加 git add

- 提交 git commit

- 推送 git push

- git push --set-upstream origin kingx（远程分支）

  _设置push远程的分支，以后可以简写为git push，不用再写远程分支名_

### 项目上线

- 代码合并到master
- 打tag

### hotfix修复问题

- 切换新的分支
  - git checkout -b

    _一般从master分支拉取一个新分支git ck -b hotfix-0415_

- 问题修复 

- 代码提交

- 代码合并

- 重新打tag

- git branch -d hotfix-o415

  _删除本地分支_

- git ps origin -d hotfix-0415

  _删除远端的分支_

- git stash

  _临时存储_（保存现场）

- git stash pop

  _恢复_（释放暂存的内容）

#### 多人协作开发

- 远端会出现多个不同的开发分支
- 拉取远程公共分支
- 合并到本地分支
- 解决冲突
- 提交
- 推送





9，23分钟