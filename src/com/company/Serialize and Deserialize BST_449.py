# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None
from collections import deque   
class Codec:

	def serialize(self, root):
		"""Encodes a tree to a single string.

		:type root: TreeNode
		:rtype: str
		"""
        # edge case, tree is empty
		if not root:
			return ''
        # initialize deque
		q=deque([root])
		res=[]
        # BFS, when queue is not empty, pop the beginning of the queue and then add left & right child node
		while q:
			node=q.popleft()
            # the current popped out node has value
			if node:
				res.append(str(node.val))
				q.append(node.left)
				q.append(node.right)
            # the current popped out node is empty
			if not node:
				res.append('#')
		return ','.join(res)



	def deserialize(self, data):
		"""Decodes your encoded data to tree.

		:type data: str
		:rtype: TreeNode
		"""
        # edge case, data is empty, the tree is empty
		if not data:
			return None
        # initialize index as 1 for checking the next ele in list 
		index=1
        # split the string to a list
		tem=data.split(',')
        # convert the first element as root node 
		root=TreeNode(tem[0])
        # add root to queue, use deque structure
		q=deque([root])
        # while q is not empty, check next two elements in the list 
		while q:
            # pop the first element in the queue
			node=q.popleft()
            # if the next element in the list is not '#', meaning the next node (=current node's left node) is not None
			if tem[index]!='#':
                # build the tree
				node.left=TreeNode(tem[index])
                # insert the current node's left node to queue
				q.append(node.left)
            # move to the next element in the list
			index+=1
            # if the next element in the list is not '#', meaning the next node (=current node's right node) is not None
			if tem[index]!='#':
                # build the tree
				node.right=TreeNode(tem[index])
				q.append(node.right)
            # move to the next element in the list
			index+=1
        # return root to return the bst
        
		return root
